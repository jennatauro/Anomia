package com.example.anomia;

import java.util.ArrayList;
import java.util.List;

public class AnomiaGame {

    public String mHash;
    public List<Player> mPlayers;
    public List<Card> mDeck;

    AnomiaGame(Player player) {
        mPlayers = new ArrayList<>();
        mPlayers.add(player);
        mDeck = new ArrayList<>();
        mHash = "1234";
    }

}
