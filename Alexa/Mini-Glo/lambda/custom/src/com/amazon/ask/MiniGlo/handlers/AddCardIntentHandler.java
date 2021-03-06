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

public class AddCardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddCardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = input.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();
        Optional <Response> response = FunctionApi.getSharedInstance()
                .badAuthentication(accessToken,input);
        if(response.equals(Optional.empty())) {
            String responseText;
            boolean correct  = true;
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String,Slot> slots = intentRequest.getIntent().getSlots();
            if(slots.get("columnName")==null){
                System.out.println("Column Name doesnt exist");

            }
            if(slots.get("cardName")==null){
                System.out.println("Card Name doesnt exist");
            }
            JsonObject column,card;
            try{
                Map<String,String > params = new HashMap<>();
                System.out.println(sessionAttributes.get(Attributes.CURRENT_BOARD).toString());
                JsonObject board = new JsonParser()
                        .parse(sessionAttributes.get(Attributes.CURRENT_BOARD).toString()).getAsJsonObject();
                if(board==null) throw new IOException();
                params.put(Constants.TOKEN,accessToken);
                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL
                                + "/boards/" + board.get("id").getAsString(),params);
                if(in==null) throw new IOException();
                JsonArray columns = new JsonParser().parse(in).getAsJsonObject().get("columns").getAsJsonArray();
                if(columns==null)throw new IOException();

                System.out.println(columns);
                column = null;
                for(int i=0; i<columns.size(); i++){

                    System.out.println(columns.get(i).getAsJsonObject().get("name").getAsString());
                    if((column = columns.get(i).getAsJsonObject()).get("name").getAsString().toUpperCase()
                            .equals(slots.get("columnName").getValue().toUpperCase()))
                    {
                        System.out.println(column.get("id").getAsString());
                        params.put("column_id",column.get("id").getAsString());
                        break;
                    }
                }

                if(column==null) throw new IOException();


                String description = slots.get("description").getValue();
                System.out.println(slots.get("cardName").getValue());
                params.put("name",slots.get("cardName").getValue());
                if(description==null) description="null";
                params.put("description", description);


                in = FunctionApi.getSharedInstance()
                        .sendPost(FunctionApi.getSharedInstance().UNIVERSAL_URL +
                                "/boards/"+ board.get("id").getAsString() + "/cards",params);

                if(in==null) throw new IOException();
                card = new JsonParser().parse(in).getAsJsonObject();

                sessionAttributes.put(Attributes.CURRENT_COLUMN,column.toString());
                sessionAttributes.put(Attributes.CURRENT_CARD,card.toString());


            }catch(IOException | NullPointerException e){
                System.out.println("Error");
                e.printStackTrace();
                column = new JsonParser().parse("{status:None}").getAsJsonObject();
                card = new JsonParser().parse("{status:None}").getAsJsonObject();
                correct = false;
            }
            responseText = new GloUtils().getSpeechCon(correct);
            if(correct){

                responseText += ". " + Constants.CORRECT_CREATION;
                responseText += ". Item Created: " + card.get("name").getAsString();
            }else responseText += ". " + Constants.INCORRECT_CREATION  + ". Please before asking to create, edit or delete a column or a card, first create or open a board, ask for help if you need";
            responseText += ". " + Constants.CONTINUE;
            sessionAttributes.put(Attributes.CURRENT_COLUMN,column.toString());
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
