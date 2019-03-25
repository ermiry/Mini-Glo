package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.utils.GloUtils;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditCardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("EditCardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        if(response.equals(Optional.empty())){
            boolean correct = true;
            JsonObject board,card=null;
            String responseText;
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot cardName,description,label,due_date;
            try {
                board = new JsonParser()
                        .parse(sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
            }catch(NullPointerException e){
                correct = false;
                board = new JsonParser().parse("{status:None}").getAsJsonObject();
            }
            cardName = slots.get("cardName");
            description = slots.get("description");
            due_date = slots.get("label");

            if(cardName!=null){
                Map<String,String> params = new HashMap<>();
                params.put("boardId",board.get("id").getAsString());
                try {
                    BufferedReader in = FunctionApi.getSharedInstance()
                            .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/board_id/cards", params);
                    JsonArray cards = new JsonParser().parse(in).getAsJsonArray();
                    if(cards==null) throw new IOException();
                    for(int i=0; i<cards.size(); i++){
                        card = cards.get(i).getAsJsonObject();
                        if(card.get("name").getAsString().equals(cardName.getValue())) break;
                    }
                    if(card==null) throw new IOException();

                    //TODO: Check if a label exists





                }catch(IOException e){
                    e.printStackTrace();
                    correct = false;
                    card = new JsonParser().parse("{status:None}").getAsJsonObject();
                }
                sessionAttributes.put(Attributes.CURRENT_CARD,card.toString());
            }else correct = false;

            responseText = new GloUtils().getSpeechCon(correct);

            if(correct){
                responseText += " .The item was correctly Edited";
            }

            return input.getResponseBuilder()
                    .withSpeech(responseText)
                    .withShouldEndSession(false)
                    .build();
        }else return response;
    }
}
