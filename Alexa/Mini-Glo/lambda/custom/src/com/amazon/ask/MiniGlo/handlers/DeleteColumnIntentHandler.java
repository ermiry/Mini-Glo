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

public class DeleteColumnIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("DeleteColumnIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        boolean correct = true;
        JsonObject board = null, column = null,status=null;
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        String responseText;

        if(response.equals(Optional.empty())){
            Slot columnName = slots.get("columnName");
            Map<String,String> params = new HashMap<>();
            params.put(Constants.TOKEN,accessToken);
            try{
                board = new JsonParser()
                        .parse(sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
                if(board==null) throw new IOException();
                System.out.println(board.get("id").getAsString());

                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL +
                                "/boards/" + board.get("id").getAsString(),params);
                board = new JsonParser().parse(in).getAsJsonObject();

                JsonArray columns = board.get("columns").getAsJsonArray();
                if(columns==null) throw new IOException();
                int i;
                for(i=0; i<columns.size(); i++){
                    column = columns.get(i).getAsJsonObject();
                    if(column.get("name").getAsString().equals(columnName.getValue())){
                        break;
                    }
                }
                if(column==null || i==columns.size()) throw new IOException();
                System.out.println(column.get("id").getAsString());
                in = FunctionApi.getSharedInstance()
                        .sendDelete(FunctionApi.getSharedInstance().UNIVERSAL_URL +
                                "/boards/"+board.get("id").getAsString()+"/columns/"+ column.get("id").getAsString(),params);
                status = new JsonParser().parse(in).getAsJsonObject();
                if(status==null || status.get("status").getAsString().equals("400")) throw new IOException();

            }catch(IOException | NullPointerException e){
                e.printStackTrace();
                correct = false;
            }

            responseText = new GloUtils().getSpeechCon(correct);
            if(correct)
                responseText += ". Item correctly deleted. Item deleted: "+ column.get("name");
            else responseText += ". Item wasnt correctly deleted. ";

            responseText += Constants.CONTINUE;

            return input.getResponseBuilder()
                    .withSpeech(responseText)
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(false)
                    .build();

        }else

        return response;
    }
}
