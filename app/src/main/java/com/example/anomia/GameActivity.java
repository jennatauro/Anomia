package com.example.anomia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    List<String> activeCards;
    int wonCards = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView symbolText = findViewById(R.id.symbol_text);
        TextView cardText = findViewById(R.id.card_text);


        symbolText.setText("No card");
        cardText.setText("No card");

        Button drawCardButton = findViewById(R.id.draw_card_button);
        drawCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Draw a card
            }
        });

        Button wonButton = findViewById(R.id.won_button);
        Button lostButton = findViewById(R.id.lost_button);
    }
}
