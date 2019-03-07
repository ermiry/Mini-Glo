package com.amazon.ask.MiniGlo.api;

import java.util.ArrayList;

public class Column {
    private String name;
    private ArrayList<Card> cardList = new ArrayList<>();

    public boolean push(Card card){
        cardList.add(card);
        return true;
    }

    public boolean pull(Card card){
        try{
            cardList.remove(card);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
