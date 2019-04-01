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

public class CreateCommentIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("CreateCommentIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
        Optional<Response> response = FunctionApi.getSharedInstance().badAuthentication(accessToken,input);
        if(response.equals(Optional.empty())){
            boolean correct = true;
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Map<String,String> params = new HashMap<>();
            Slot cardName = slots.get("cardName");
            String cN = null;
            String responseText;
            Slot commentSlot = slots.get("comment");
            JsonObject board = null, card = null,comment = null;
            try{
                params.put(Constants.TOKEN,accessToken);
                if(cardName!=null) {
                    cN = cardName.getValue();
                }else{
                    cN = sessionAttributes.get(Attributes.CURRENT_CARD).toString();
                    if(cN==null) throw new IOException();
                }
                board = new JsonParser().parse(
                        sessionAttributes.get(Attributes.CURRENT_BOARD).toString()
                ).getAsJsonObject();
                if(board==null) throw new IOException();
                BufferedReader in = FunctionApi.getSharedInstance()
                        .sendGet(FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/" + board.get("id").getAsString()
                                + "/cards",params);
                if(in==null) throw new IOException();
                JsonArray cards = new JsonParser().parse(in).getAsJsonArray();
                if(cards==null) throw new IOException();
                int i;
                for(i = 0; i<cards.size(); i++){
                    card = cards.get(i).getAsJsonObject();
                    if(card==null) throw new IOException();
                    if(card.get("name").getAsString().equals(cN)){
                        break;
                    }
                }

                if(card == null ||i==cards.size())throw new IOException();
                if(commentSlot.getValue()==null) throw new IOException();

                params.put("text",commentSlot.getValue());
                in = FunctionApi.getSharedInstance().sendPost(
                        FunctionApi.getSharedInstance().UNIVERSAL_URL + "/boards/" + board.get("id").getAsString()
                                + "/cards/" + card.get("id").getAsString() + "/comments",params
                );

                if(in==null) throw new IOException();

                comment = new JsonParser().parse(in).getAsJsonObject();

                if(comment == null) throw new IOException();
            sessionAttributes.put(Attributes.CURRENT_CARD,card.toString());
            sessionAttributes.put(Attributes.CURRENT_COMMENT,comment.toString());
            }catch(IOException | NullPointerException e ){
                correct = false;
                e.printStackTrace();

            }

            responseText = new GloUtils().getSpeechCon(correct);
            if(correct)
                responseText += "Comment was correctly added. ";
            else responseText += "Comment wasnt correctly added. ";

            responseText += Constants.CONTINUE;

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
