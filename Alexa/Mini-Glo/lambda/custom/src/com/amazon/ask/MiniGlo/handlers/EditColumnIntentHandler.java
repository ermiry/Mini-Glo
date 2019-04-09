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

public class EditColumnIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("EditColumnIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String  accessToken = input.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        if(response.equals(Optional.empty())) {

            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot columnName = slots.get("columnName");
            Slot newColumnName = slots.get("newColumnName");
            System.out.println("Edit: " + columnName.getValue() + "\nNew:" + newColumnName.getValue());
            Map<String,String > params = new HashMap<>();
            JsonObject column = null;
            boolean correct = true;
            String responseText;

            params.put(Constants.TOKEN,accessToken);
            try{
                JsonObject board = new JsonParser().parse(
                sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
                if(board==null) throw new IOException();

                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL +
                                "/boards/"+ board.get("id").getAsString(),params);
                if(in==null) throw new IOException();board = new JsonParser().parse(in).getAsJsonObject();
                if(board == null) throw new IOException();

                JsonArray columns = board.get("columns").getAsJsonArray();

                if(columns==null) throw new IOException();
                System.out.println(columns);
                int i;
                for(i=0; i<columns.size(); i++){
                    column = columns.get(i).getAsJsonObject();
                    if(column!=null){
                        System.out.println(column.get("name").getAsString() + "\n" + columnName.getValue());
                        if(column.get("name").getAsString().equals(columnName.getValue())){
                            break;
                        }
                    }
                }
                if(column==null || i==columns.size()) throw new IOException();

                if(newColumnName!=null && newColumnName.getValue()!=null) params.put("name",newColumnName.getValue());

                in = FunctionApi.getSharedInstance()
                        .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/"
                                + board.get("id").getAsString() + "/columns/" + column.get("id").getAsString(),params);
                if(in==null) throw new IOException();column = new JsonParser().parse(in).getAsJsonObject();

                if(column==null) throw new IOException();

                sessionAttributes.put(Attributes.CURRENT_COLUMN,column.toString());

            }catch(IOException | NullPointerException e){
                e.printStackTrace();
                column = new JsonParser().parse(Constants.JSON_NULL).getAsJsonObject();
                correct =  false;
            }

            responseText = new GloUtils().getSpeechCon(correct);

            if(correct){
                responseText += ". " + Constants.CORRECT_EDIT;
                responseText += ". Item edited: "  + column.get("name").getAsString();
            }else responseText += ". " + Constants.INCORRECT_EDIT  + ". Please before asking to create, edit or delete a column or a card, first create or open a board, ask for help if you need";
            responseText += Constants.CONTINUE;
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
