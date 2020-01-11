package com.example.anomia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnomiaGame {

    public boolean isActive = false;
    public String mHash;
    public List<Card> mDeck;
    public int mGameIndex;

    AnomiaGame() {
        mGameIndex = 0;
        mDeck = new ArrayList<>();
        mHash = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
    }

}
