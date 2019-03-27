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
import java.util.Random;

public class AddColumnIntentHandler implements RequestHandler {
    private static final Random RANDOM = new Random();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddColumnIntent")) ;
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken  = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        if(response.equals(Optional.empty())) {
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String,Slot> slots = intentRequest.getIntent().getSlots();
            Map<String,String> params = new HashMap<>();
            boolean correct = true;
            Slot columnName = slots.get("columnName");
            String responseText;
            JsonObject column = null, board = null;

            try{
                params.put(Constants.TOKEN,accessToken);
                params.put("name",columnName.getValue());
                board = new JsonParser().parse(sessionAttributes.get(Attributes.CURRENT_BOARD)
                        .toString()).getAsJsonObject();
                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL +
                                "/boards/" + board.get("id").getAsString() + "/columns",params);
                column = new JsonParser().parse(in).getAsJsonObject();
                if(column==null) throw new IOException();
            }catch(IOException | NullPointerException e){
                e.printStackTrace();
                column = new JsonParser().parse("{status:None}").getAsJsonObject();
                correct = false;
            }
            sessionAttributes.put(Attributes.CURRENT_COLUMN,column.toString());
            responseText = new GloUtils().getSpeechCon(correct);
            if(correct){
                responseText += ". " + Constants.CORRECT_CREATION;
                responseText += ". Item Created: " + column.get("name").getAsString();
            }else
                responseText += ". " + Constants.INCORRECT_CREATION;
            responseText += ". " + Constants.CONTINUE;



            return input.getResponseBuilder()
                    .withSpeech(responseText)
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(false)
                    .build();

        }else return response;
    }


}
