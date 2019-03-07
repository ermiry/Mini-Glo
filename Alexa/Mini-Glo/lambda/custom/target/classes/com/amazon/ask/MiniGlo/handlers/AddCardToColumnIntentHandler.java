package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class AddCardToColumnIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddCardToColumn").and(Predicates.sessionAttribute(Attributes.STATE_KEY,Attributes.BOARD_STATE)));

    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String responseText = "";
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String,Slot> slots = intentRequest.getIntent().getSlots();
        String cardName,columnName;
        if((columnName = slots.get("columnName").getValue())==null){
            if((columnName = (String) sessionAttributes.get(Attributes.COLUMN_NAME))!=null){
                if((cardName = slots.get("cardname").getValue())!=null)
                    new FunctionApi().addCardtoColumn(columnName,cardName);
                else{
                    cardName = "default";
                    new FunctionApi().addCardtoColumn(columnName,cardName);
                }
            }else{
                responseText = "Column wasnt founded";
            }
        }else{
            responseText = "The card was corrected added to the column";
            if((cardName = slots.get("cardName").getValue())==null)
                new FunctionApi().addCardtoColumn(columnName,cardName);
            else{
                cardName = "default";
                new FunctionApi().addCardtoColumn(columnName,cardName);
            }
        }

        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build();
    }
}
