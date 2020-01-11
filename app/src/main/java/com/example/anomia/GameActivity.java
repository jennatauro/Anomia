package com.example.anomia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private static final String GAME_INFO = "Game_Info";
    private static final String PLAYERS = "Players";
    private static final String GAME_IS_ACTIVE = "isActive";
    private static final String CARD_DECK = "Card_Deck";
    private static final String GAME_INDEX = "mGameIndex";

    private DatabaseReference mCurrentGameReference;
    private int mCurrentGameIndex;
    private boolean mIsCurrentUserTurn;
    private String mUserId;
    List<Card> activeCards;
    int wonCards = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        String gameHash = getIntent().getExtras().getString("gamehash");
        mUserId = getIntent().getExtras().getString("userid");
        mCurrentGameReference = FirebaseDatabase.getInstance().getReference().child(gameHash);
        mCurrentGameReference.child(GAME_INFO).child(GAME_INDEX).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCurrentGameIndex = ((Long) dataSnapshot.getValue()).intValue();
                mCurrentGameReference.child(PLAYERS).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<HashMap> players = new ArrayList<HashMap>(((HashMap) dataSnapshot.getValue()).values());
                        mIsCurrentUserTurn = players.get(mCurrentGameIndex % players.size()).get("mUid").equals(mUserId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        TextView symbolText = findViewById(R.id.symbol_text);
        TextView cardText = findViewById(R.id.card_text);


        symbolText.setText("No card");
        cardText.setText("No card");

        Button drawCardButton = findViewById(R.id.draw_card_button);
        drawCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Draw a card
                if (mIsCurrentUserTurn) {
                    mCurrentGameReference.child(CARD_DECK).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<HashMap> cards = new ArrayList<HashMap>(((HashMap) dataSnapshot.getValue()).values());
                            HashMap cardMap = cards.get(mCurrentGameIndex);
                            if (activeCards == null) {
                                activeCards = new ArrayList<>();
                            }
                            activeCards.add(new Card((String) cardMap.get("mText"), CardSymbol.valueOf((String) cardMap.get("mCardSymbol"))));
                            mCurrentGameReference.child(GAME_INFO).child(GAME_INDEX).setValue(mCurrentGameIndex + 1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        Button wonButton = findViewById(R.id.won_button);
        Button lostButton = findViewById(R.id.lost_button);
    }
}
