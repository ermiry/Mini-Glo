package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class AddColumnToBoardIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {


    private static final Random RANDOM = new Random();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddColumnToBoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        //TODO: Check for current board, if there's not ask it
        String responseText = "", columnName = "",boardName = "";
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String,Slot> slots = intentRequest.getIntent().getSlots();
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        if((boardName = (String) sessionAttributes.get(Attributes.BOARD_NAME))!=null)
            addColumn(columnName,slots,boardName,responseText);
        else if((boardName = slots.get("boardName").getValue())!=null){
            addColumn(columnName,slots,boardName,responseText);
        }else{
            responseText = getSpeechCon(false);
            responseText += Constants.INCORRECT_CREATION;
        }

        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withShouldEndSession(false)
                .build();
    }

    private String getSpeechCon(boolean b) {
        if(b) return "<say-as interpret-as='interjection'>" + getRandomItem(Constants.CORRECT_RESPONSES) + "!</say-as><break strength='strong'/>";
        else return "<say-as interpret as='interjection'>" + getRandomItem(Constants.INCORRECT_RESPONSES) + "!</say-as><break strength='strong'/>";
    }

    private void addColumn(String columnName,Map<String,Slot> slots, String boardName,String responseText){
        if((columnName = slots.get("columnName").getValue())==null){
            new FunctionApi().addColumnToBoard(columnName,boardName);
            responseText = getSpeechCon(true);
            responseText += Constants.CORRECT_CREATION;
        }else{
            responseText = getSpeechCon(false);
            responseText += Constants.INCORRECT_CREATION + "." + Constants.HELP_MESSAGE;
        }
    }

    private <T> T getRandomItem(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
