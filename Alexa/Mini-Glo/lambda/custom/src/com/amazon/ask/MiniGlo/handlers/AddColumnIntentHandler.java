package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.MiniGlo.utils.GloUtils;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class AddColumnIntentHandler implements RequestHandler {
    private static final Random RANDOM = new Random();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddColumnIntent")) ;
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        //Map<String,Object> persistentAttributes = input.getAttributesManager().getPersistentAttributes();
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        Slot[] slotsArray = new GloUtils().getSlotsArray(input);
        boolean allCorrect = true;
        String boardName="",columnName="",responseText="";
        JsonObject JsonBoard = null;
        if(slotsArray==null) allCorrect = false;
        else{
            boardName = slotsArray[0].getValue();
            columnName = slotsArray[1].getValue();
        }
        if(allCorrect) {
            Object board = sessionAttributes.get(Attributes.BOARD_NAME);
            if (board != null) {
                boardName = (String) board;
                JsonBoard = new FunctionApi().lookForBoard(boardName);
                if(JsonBoard!=null) sessionAttributes.put("CurrentBoard",JsonBoard);
                else JsonBoard = (JsonObject) sessionAttributes.get("CurrentBoard");
            }else JsonBoard = (JsonObject) sessionAttributes.get("CurrentBoard");
            sessionAttributes.put(Attributes.COLUMN_NAME, columnName);

        }
        if(allCorrect) allCorrect= new FunctionApi().addColumnToBoard(columnName,JsonBoard)!=null;
        responseText = new GloUtils().getSpeechCon(allCorrect);
        if(allCorrect){
            responseText +=  ". " + Constants.CORRECT_CREATION + ", " + columnName;
            responseText += ". Added " + columnName + " to " + boardName;
        }
        else responseText += Constants.INCORRECT_CREATION + " ";

        responseText  += ", " +  Constants.CONTINUE;

        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build();
    }


}
