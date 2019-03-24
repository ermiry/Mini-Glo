package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.MiniGlo.utils.GloUtils;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            sessionAttributes.put("ColumnName",columnName);
            if(boardName!=null) {
                JsonBoard = new FunctionApi().lookForBoard(boardName);
                if (JsonBoard != null) sessionAttributes.put("CurrentBoard", JsonBoard.toString());
                else JsonBoard = new JsonParser().parse((String)sessionAttributes.get("CurrentBoard")).getAsJsonObject();
            }else JsonBoard = new JsonParser().parse((String)sessionAttributes.get("CurrentBoard")).getAsJsonObject();
            if(JsonBoard==null)allCorrect=false;
        }

        if(allCorrect) allCorrect = new FunctionApi().addColumnToBoard(columnName,JsonBoard);
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
