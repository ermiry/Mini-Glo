package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
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

public class DeleteBoardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("DeleteBoardIntent"));
    }
    @Override
    public Optional<Response> handle(HandlerInput input) {

        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        JsonObject status;

        if(response.equals(Optional.empty())) {
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot boardName = slots.get("boardName");
            JsonObject board;
            try {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.TOKEN, accessToken);

                if(boardName!=null) {
                    JsonArray boards = new JsonParser().parse(
                            FunctionApi.getSharedInstance()
                            .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards",params))
                            .getAsJsonArray();
                    if(boards!=null){
                        board = null;
                        int i;
                        for(i=0; i<boards.size(); i++){
                            board = boards.get(i).getAsJsonObject();
                            if(board.get("name").getAsString().equals(boardName.getValue())){
                                break;
                            }
                        }
                        if(board==null ||boards.size()==i) throw new IOException();
                    }else throw new IOException();
                }else {
                    board = new JsonParser()
                            .parse(
                                    sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
                    if(board==null) throw new IOException();
                }
                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendDelete(FunctionApi.getSharedInstance()
                                .UNIVERSAL_URL + "/boards/" + board.get("id").getAsString(), params);
                status = new JsonParser().parse(in).getAsJsonObject();
                if(status==null) throw new IOException();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                status = new JsonParser().parse(Constants.JSON_NULL).getAsJsonObject();
            }


            return input.getResponseBuilder()
                    .withSpeech("Status " + status.get("status").getAsString())
                    .withShouldEndSession(false)
                    .build();

        } else {
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
