package com.example.anomia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();

        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        // Create a new game in Firebase
        Player player = new Player("Host");
//        mDatabase.getReference("message").setValue("Hello World");
        AnomiaGame anomiaGame = new AnomiaGame(player);

        mDatabase.getReference().child("1234").setValue(anomiaGame);

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
    }
}
