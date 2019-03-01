package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.MiniGlo.Functions;

public class AddCardToColumnIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{

    Functions functions = new Functions();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddCardToColumn"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        String responseSpeech, columnName, cardName;

        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();

        Map<String, Slot> slots = intentRequest.getIntent().getSlots();

        try{
            if((columnName = slots.get("ColumnName").getValue())==null){
                throw new Exception();
            }
        }catch(Exception e){
            columnName = "NIN";
        }

        try{
            if((cardName = slots.get("cardName").getValue())==null){
                throw new Exception();
            }
        }catch(Exception e){
            cardName = "NIN";
        }

        if(functions.addCardToColumn(columnName,cardName))responseSpeech = "The card " + cardName +
                ", was succesfully added to " + columnName;
        else responseSpeech = "The card " + cardName + ", could not be added to " + columnName;

        return input.getResponseBuilder().withSpeech(responseSpeech).withShouldEndSession(true).build();
    }
}
