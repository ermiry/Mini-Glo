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

public class EditBoardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("EditBoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        boolean correct = true;
        String responseText;

        if(response.equals(Optional.empty())){
            Map<String,String> params = new HashMap<>();
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot boardName = slots.get("boardName");
            Slot newBoardName = slots.get("newBoardName");

            JsonObject board = null;
            try{
                params.put(Constants.TOKEN,accessToken);
                if(boardName!=null) {
                    BufferedReader in = FunctionApi.getSharedInstance()
                            .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards", params);

                    JsonArray boards = new JsonParser().parse(in).getAsJsonArray();
                    if (boards != null) {
                        for (int i = 0; i < boards.size(); i++) {
                            board = boards.get(i).getAsJsonObject();
                            if (board.get("name").getAsString().equals(boardName.getValue())) break;
                        }
                    }
                    if(board == null ) throw new IOException();
                    if(newBoardName==null || newBoardName.getValue()==null) throw new IOException();

                    params.put("new_name",newBoardName.getValue());
                    in = FunctionApi.getSharedInstance()
                            .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/" +
                                    board.get("id").getAsString(),params);
                    board = new JsonParser().parse(in).getAsJsonObject();
                    if(board==null) throw new IOException();
                }else{
                    if(sessionAttributes.get(Attributes.CURRENT_BOARD)==null || newBoardName==null) throw new IOException();
                    params.put("new_name",newBoardName.getValue());
                    board = new JsonParser()
                            .parse(sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
                    BufferedReader in = FunctionApi.getSharedInstance()
                            .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/" +
                                    board.get("id").getAsString(),params);
                    board = new JsonParser().parse(in).getAsJsonObject();
                    if(board==null) throw new IOException();
                }
                sessionAttributes.put(Attributes.CURRENT_BOARD,board);
            }catch(IOException | NullPointerException e){
                e.printStackTrace();
                correct = false;
            }


            responseText = new GloUtils().getSpeechCon(correct);
            if(correct){
                responseText+= ". " + Constants.CORRECT_EDIT;
                responseText += ". Item edited: " + board.get("name");
            }else
                responseText += ". " + Constants.INCORRECT_EDIT;


            responseText += Constants.CONTINUE;

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
