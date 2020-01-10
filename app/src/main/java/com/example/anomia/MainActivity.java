package com.example.anomia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String GAME_INFO = "Game_Info";
    private static final String PLAYERS = "Players";

    private DatabaseReference mDatabaseReference;
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
                        } else {
                            // TODO
                        }
                    }
                });
    }
}
