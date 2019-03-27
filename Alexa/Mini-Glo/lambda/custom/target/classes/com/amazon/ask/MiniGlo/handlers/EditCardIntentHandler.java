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
            Slot cardName,description,label,due_date, newCardName;
            try {
                board = new JsonParser()
                        .parse(sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
            }catch(NullPointerException e){
                correct = false;
                board = new JsonParser().parse("{status:None}").getAsJsonObject();
            }

            cardName = slots.get("cardName");
            description = slots.get("description");
            label = slots.get("label");
            due_date = slots.get("due_date");
            newCardName = slots.get("newCardName");

            if(cardName!=null){
                Map<String,String> params = new HashMap<>();
                try {
                    params.put("token",accessToken);
                    BufferedReader in = FunctionApi.getSharedInstance()
                            .sendGet(FunctionApi
                                    .getSharedInstance().UNIVERSAL_URL + "/boards/" + board.get("id").getAsString()
                                    + "/cards", params);
                    JsonArray cards = new JsonParser().parse(in).getAsJsonArray();
                    if(cards==null) throw new IOException();
                    for(int i=0; i<cards.size(); i++){
                        card = cards.get(i).getAsJsonObject();
                        if(card!=null) {
                            if (card.get("name").getAsString().equals(cardName.getValue())) {
                                break;
                            }
                        }
                    }
                    if(card==null) throw new IOException();

                    if(board.has("labels")) {
                        JsonArray labels = board.get("labels").getAsJsonArray();
                        JsonObject labelJ;
                        for (int i = 0; i < labels.size(); i++) {
                            labelJ = labels.get(i).getAsJsonObject();
                            if (labelJ != null) {
                                if (labelJ.get("name").getAsString().equals(label.getValue())) {
                                    params.put("label", labelJ.get("id").getAsString());
                                    break;
                                }
                            }
                        }
                    }else params.put("labels",null);
                    if(description!=null) params.put("description",description.getValue());

                    else if(card.has("description"))
                        params.put("description",card.get("description").getAsString());
                    else params.put("description",null);

                    if(due_date!=null) params.put("due_date",due_date.getValue());

                    else if(card.has("due_date"))
                        params.put("due_date",card.get("due_date").getAsString());
                    else params.put("due_date",null);

                    if(newCardName!=null) params.put("name",newCardName.getValue());

                    else params.put("name",card.get("name").getAsString());

                    in = FunctionApi.getSharedInstance()
                            .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL
                                    + "/boards/"+ board.get("id").getAsString()

                                    + "/cards/" + card.get("id").getAsString() ,params);

                    card = new JsonParser().parse(in).getAsJsonObject();
                    if(card==null) throw new IOException();

                }catch(IOException | NullPointerException e){
                    e.printStackTrace();
                    correct = false;
                    card = new JsonParser().parse("{status:None}").getAsJsonObject();
                }

                sessionAttributes.put(Attributes.CURRENT_CARD,card.toString());
            }else correct = false;

            responseText = new GloUtils().getSpeechCon(correct);

            if(correct){
                responseText += " .The item was correctly Edited";
                responseText +=" .Item edited: " + card.get("name").getAsString();
            }else responseText += " .The item wasnt correctly edited";

            return input.getResponseBuilder()
                    .withSpeech(responseText)
                    .withShouldEndSession(false)
                    .build();
        }else {
            accessToken = FunctionApi.getSharedInstance().reAuthenticate();
            if (accessToken != null) {
                sessionAttributes.put(Attributes.ACCESS_TOKEN, accessToken);
                return input.getResponseBuilder()
                        .withSpeech("You lost connection, but we have reconnected. Please try again")
                        .withShouldEndSession(false)
                        .build();
            } else {
                sessionAttributes.remove(Attributes.ACCESS_TOKEN);
                return response;
            }

        }
    }
}
