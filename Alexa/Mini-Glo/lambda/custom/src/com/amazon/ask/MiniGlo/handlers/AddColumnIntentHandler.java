package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.List;
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
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        boolean allCorrect = true;
        Slot[] slotsArray = new Slot[2];

        int i=0;

        for(Slot slot:slots.values()){
            if (slot != null) {
                slotsArray[i] = slot;
                i++;
            }
        }
        if(i==0) allCorrect = false;


        Slot boardNameSlot=null,columnNameSlot=null;
        if(i>1) {
            if (slotsArray[0].getName().equals("boardName")) {
                boardNameSlot = slotsArray[0];
                columnNameSlot = slotsArray[1];
            } else {
                boardNameSlot = slotsArray[1];
                columnNameSlot = slotsArray[0];
            }
        }else{
            if(slotsArray[0].getName().equals("boardName")) boardNameSlot = slotsArray[0];
            else columnNameSlot = slotsArray[0];
        }

        String boardName="",columnName="",responseText="";
        if(sessionAttributes.get(Attributes.BOARD_NAME)!=null)
            boardName = sessionAttributes.get(Attributes.BOARD_NAME).toString();
        /*else if(persistentAttributes.get(Attributes.BOARD_NAME)!=null)
            boardName = persistentAttributes.get(Attributes.BOARD_NAME).toString();*/
        else if(boardNameSlot!=null){
            boardName = boardNameSlot.getValue();
            sessionAttributes.put(Attributes.BOARD_NAME,boardName);
        }
        else{
            allCorrect = false;
        }

        responseText = getSpeechCon(false);

        if(allCorrect) allCorrect= new FunctionApi().addColumnToBoard(columnName,boardName);

        if(allCorrect) responseText += Constants.CORRECT_CREATION + ". " + columnName;
        else responseText += Constants.INCORRECT_CREATION + ".";


        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(true)
                .build();
    }

    private String getSpeechCon(boolean b) {
        if (b) {
            return "<say-as interpret-as='interjection'>" + getRandomItem(Constants.CORRECT_RESPONSES) + "! </say-as><break strength='strong'/>";
        } else {
            return "<say-as interpret-as='interjection'>" + getRandomItem(Constants.INCORRECT_RESPONSES) + " </say-as><break strength='strong'/>";
        }
    }

    private <T> T getRandomItem(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

}
