package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.MiniGlo.Functions;

public class AddColumnToBoardIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {
    private Functions functions = new Functions();


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddColumnToBoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();

        Map<String, Slot> slots = intentRequest.getIntent().getSlots();

        String columnName = slots.get("ColumnName").getValue();
        String boardName;
        String responseSpeech;
        if((boardName = slots.get("BoardName").getValue()) == null){
            boardName = "NIN";
        }

        boolean correct = functions.addColumnToBoard(columnName,boardName);
        if(correct) responseSpeech = "Column " + columnName  + ",Was added correctly";
        else responseSpeech ="Column " + columnName + ", Couldnt be added";
        return input.getResponseBuilder()
                .withSpeech(responseSpeech)
                .withShouldEndSession(true)
                .build();
    }
}
