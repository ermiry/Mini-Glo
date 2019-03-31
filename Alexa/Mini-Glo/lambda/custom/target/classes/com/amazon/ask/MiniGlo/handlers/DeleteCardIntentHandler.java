package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
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

public class DeleteCardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("DeleteCardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken  = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        boolean correct = true;

        if(response.equals(Optional.empty())){
            Map<String,String> params = new HashMap<>();
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            JsonObject column=null,card=null,board=null, status;
            try {
                params.put(Constants.TOKEN,accessToken);

                board = new JsonParser().parse(sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();

                Slot cardName = slots.get("cardName");
                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL +
                                "/boards/" + board.get("id").getAsString() + "/cards" ,params);
                if(in==null) throw new IOException();JsonArray cards = new JsonParser().parse(in).getAsJsonArray();

                if(cards==null) throw new IOException();
                int i;
                for(i=0; i<cards.size(); i++){
                    card = cards.get(i).getAsJsonObject();
                    if(card.get("name").getAsString().toUpperCase().equals(cardName.getValue().toUpperCase())){
                        break;
                    }
                }
                if(card==null || i==cards.size()) throw new IOException();

                in = FunctionApi.getSharedInstance()
                        .sendDelete(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/"
                                +board.get("id").getAsString() +"/cards/" + card.get("id").getAsString() , params);
                status = new JsonParser().parse(in).getAsJsonObject();

                if(status.get("status").getAsString().equals("400")) throw new IOException();

            }catch (IOException | NullPointerException e){
                e.printStackTrace();
                correct = false;
                status = new JsonParser().parse(Constants.JSON_NULL).getAsJsonObject();
            }

            String responseText = new GloUtils().getSpeechCon(correct);
            if(correct) responseText+=". The item was correctly Deleted, Item deleted: " + card.get("name").getAsString();
            else responseText+=". The item wasnt correctly Deleted";
            responseText+= Constants.CONTINUE;
            FunctionApi.getSharedInstance().disconnect();
            return input.getResponseBuilder()
                    .withSpeech(responseText)
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(false)
                    .build();
        }else {
            accessToken = input.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();
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
