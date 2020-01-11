package com.example.anomia;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public String mUid;
    public String mName;
    public List<Card> mActiveCards;
    public List<Card> mWonCards;
    public int mScore;

    Player(String uid, String name) {
        mUid = uid;
        mName = name;
        mScore = 0;
        mActiveCards = new ArrayList<>();
        mWonCards = new ArrayList<>();
    }
}
