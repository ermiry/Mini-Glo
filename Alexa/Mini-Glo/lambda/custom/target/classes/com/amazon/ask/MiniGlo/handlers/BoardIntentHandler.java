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

public class BoardIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("BoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response;
        if((response = new FunctionApi().badAuthentication(accessToken, input)).equals(Optional.empty())) {
            Map<String, String> params = new HashMap<>();
            String speechOutput;
            JsonObject board = null;
            boolean correct = true;
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot boardName = slots.get("boardName");
            params.put("boardName", boardName.getValue());
            params.put("accessToken", accessToken);
            try {
                BufferedReader in = FunctionApi.getSharedInstance().sendGet(FunctionApi.getSharedInstance()
                        .UNIVERSAL_URL + "/boards", params);
                JsonArray boards = new JsonParser().parse(in).getAsJsonArray();
                for(int i=0; i< boards.size(); i++){
                    if((board = boards.get(i).getAsJsonObject()).get("name").getAsString().equals(boardName.getValue())){
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                board = new JsonParser().parse("{status:None}").getAsJsonObject();
                correct = false;
            }
            speechOutput = new GloUtils().getSpeechCon(correct);

            if (correct) {
                sessionAttributes.put(Attributes.CURRENT_BOARD, board.toString());
                speechOutput += Constants.CORRECT_SHOW;
                speechOutput += ".  " + boardName.getValue();
            } else speechOutput += Constants.INCORRECT_SHOW;
            speechOutput += ". " + Constants.CONTINUE;

            return input.getResponseBuilder()
                    .withSpeech(speechOutput)
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(false)
                    .build();
        }
        else return response;

    }

}

