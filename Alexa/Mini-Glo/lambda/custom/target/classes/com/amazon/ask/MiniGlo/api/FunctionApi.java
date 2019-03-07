package com.amazon.ask.MiniGlo.api;

public class FunctionApi {

    public boolean lookForBoard(String boardName){
        //TODO: Look in profile for boards with same name
        /**
         * for(String boardInProfileName:profile)
         * {
         *  if(boardName == boardInProfileName) return true;
         * }
         * */
        return true;
    }

    public boolean addColumnToBoard(String columnName,String board){
        return true;
    }

    public boolean addCardtoColumn(String columnName,String cardName){

        /*for(String columnInBoard:Board){
            if(columnName==columnInBoard){
                new Column.push(Attributes.cardName,cardName);
                return true;
            }

        }
        //return false*/

        return true;
    }

}
