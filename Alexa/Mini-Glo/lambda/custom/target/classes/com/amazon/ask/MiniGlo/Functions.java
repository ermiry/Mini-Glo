package com.amazon.ask.MiniGlo;

public class Functions {

    public boolean addColumnToBoard(String columnName,String boardName){
        if(columnName.equals("NIN")) return false;
        else return true;
    }


    public boolean showAllBoards(){
        return true;
    }


    public boolean addCardToColumn(String columnName, String cardName) {
        if(columnName.equals("NIN") || cardName.equals("NIN")) return false;
        else return true;
    }
}
