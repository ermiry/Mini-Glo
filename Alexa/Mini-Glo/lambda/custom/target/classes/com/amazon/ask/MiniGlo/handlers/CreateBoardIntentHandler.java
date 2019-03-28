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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateBoardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("CreateBoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);

        if(response.equals(Optional.empty())) {

            boolean correct = true;
            String responseText;
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot boardName = slots.get("boardName");

            Map<String,String> params = new HashMap<>();
            JsonObject board = null;
            try{
                if(boardName==null) throw new IOException();
                params.put("boardName",boardName.getValue());
                params.put(Constants.TOKEN,accessToken);
                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards",params);
                board = new JsonParser().parse(in).getAsJsonObject();
                if(board==null) throw new IOException();
                sessionAttributes.put(Attributes.CURRENT_BOARD, board.toString());
            }catch(IOException | NullPointerException e) {
                e.printStackTrace();
                correct = false;
            }
            responseText = new GloUtils().getSpeechCon(correct);
            if (correct) {
                responseText += " " + Constants.CORRECT_CREATION;
            } else responseText += " " + Constants.INCORRECT_CREATION;

            responseText += " ." + Constants.CONTINUE;

            return input.getResponseBuilder()
                    .withSpeech(responseText)
                    .withReprompt(Constants.HELP_MESSAGE)
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
