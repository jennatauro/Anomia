package com.example.anomia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String GAME_INFO = "Game_Info";
    private static final String PLAYERS = "Players";
    private static final String GAME_IS_ACTIVE = "isActive";
    private static final String CARD_DECK = "Card_Deck";

    private DatabaseReference mDatabaseReference;
    private DatabaseReference mCurrentGameReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new game in Firebase
                AnomiaGame anomiaGame = new AnomiaGame();

                EditText nameEditText = findViewById(R.id.name_edit_text);

                mDatabaseReference.child(anomiaGame.mHash).child(GAME_INFO).setValue(anomiaGame);

                signInNewPlayer(anomiaGame.mHash, nameEditText.getText().toString());

                TextView gameHashText = findViewById(R.id.game_hash_text_view);
                gameHashText.setText(anomiaGame.mHash);
                gameHashText.setVisibility(View.VISIBLE);
            }
        });

        Button joinGameButton = findViewById(R.id.join_game_button);
        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Join game in Firebase
                EditText nameEditText = findViewById(R.id.name_edit_text);
                EditText gameHashText = findViewById(R.id.game_hash_edit_text);

                signInNewPlayer(gameHashText.getText().toString(), nameEditText.getText().toString());
            }
        });

        Button beginGameButton = findViewById(R.id.begin_game_shuffle_deck);
        beginGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentGameReference != null) {
                    // Create the deck of cards
                    List<String> cardStrings  = Arrays.asList(getApplication().getResources().getStringArray(R.array.card_strings));
                    List<Card> cards = new ArrayList<>();

                    for (int i = 0; i < cardStrings.size(); i++) {
                        Card card = new Card(cardStrings.get(i), CardSymbol.values()[i % 4]);
                        cards.add(card);
                    }

                    Collections.shuffle(cards);

                    for (int i = 0; i < cards.size(); i++) {
                        Card card = cards.get(i);
                        mCurrentGameReference.child(CARD_DECK).child(i + "_" + card.mText + "_" + card.mCardSymbol).setValue(card);
                    }
                    mCurrentGameReference.child(GAME_INFO).child(GAME_IS_ACTIVE).setValue(true);
                }
            }
        });
    }

    private void signInNewPlayer(final String gameHash, final String name) {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Player player = new Player(user.getUid(), name);
                            mDatabaseReference.child(gameHash).child(PLAYERS).child(player.mUid).setValue(player);

                            // Listen to when the game becomes active
                            mCurrentGameReference = mDatabaseReference.child(gameHash);
                            mCurrentGameReference.child(GAME_INFO).child(GAME_IS_ACTIVE).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // The game is started
                                    if ((boolean)dataSnapshot.getValue()) {
                                        // Navigate to Game Activity
                                        Intent intent = new Intent(getApplication(), GameActivity.class);
                                        intent.putExtra("gamehash", gameHash);
                                        intent.putExtra("userid", mAuth.getCurrentUser().getUid());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // TODO
                                }
                            });
                        } else {
                            // TODO
                        }
                    }
                });
    }
}
