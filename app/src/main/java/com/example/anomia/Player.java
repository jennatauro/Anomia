package com.example.anomia;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public String mName;
    public List<Card> mActiveCards;
    public List<Card> mWonCards;

    Player(String name) {
        mName = name;
        mActiveCards = new ArrayList<>();
        mWonCards = new ArrayList<>();
    }
}
