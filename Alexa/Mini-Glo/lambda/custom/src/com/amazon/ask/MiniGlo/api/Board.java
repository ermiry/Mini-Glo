package com.amazon.ask.MiniGlo.api;

import java.util.ArrayList;

public class Board{
    private String name;
    private ArrayList<Column> columnList = new ArrayList<>();

    public boolean push(Column column){
        try{
            columnList.add(column);
        }catch(Exception e) {
            return false;
        }
        return true;
    }

    public boolean pull(Column column){
        try {
            columnList.remove(column);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
