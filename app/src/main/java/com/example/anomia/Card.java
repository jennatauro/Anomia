package com.example.anomia;

public class Card {

    public CardSymbol mCardSymbol;
    public String mText;

    Card(String text, CardSymbol cardSymbol) {
        mCardSymbol = cardSymbol;
        mText = text;
    }

}
