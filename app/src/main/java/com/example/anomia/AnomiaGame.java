package com.example.anomia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnomiaGame {

    public String mHash;
    public List<Player> mPlayers;
    public List<Card> mDeck;

    AnomiaGame(Player player) {
        mPlayers = new ArrayList<>();
        mPlayers.add(player);
        mDeck = new ArrayList<>();
        mHash = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
    }

}
