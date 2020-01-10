package com.example.anomia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Create a new game in Firebase
        Player player = new Player("Host");
        AnomiaGame anomiaGame = new AnomiaGame(player);

        mDatabaseReference.child(anomiaGame.mHash).setValue(anomiaGame);

        TextView gameHashText = findViewById(R.id.game_hash_text_view);
        gameHashText.setText(anomiaGame.mHash);
        gameHashText.setVisibility(View.VISIBLE);
    }
}
