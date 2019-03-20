package com.amazon.ask.MiniGlo.handlers;


import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.MiniGlo.utils.GloUtils;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Optional;

public class CreateBoardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("CreateBoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        System.out.println("Im in");
        boolean correct = true;
        String responseText="";
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        Slot boardName = slots.get("boardName");
        JsonObject board = null;
        if(boardName==null) correct = false;
        if(correct) correct=(board=new FunctionApi().createBoard(boardName.getValue()))!=null;
        responseText = new GloUtils().getSpeechCon(correct);

        if(correct){
            responseText += " " + Constants.CORRECT_CREATION;
            String json = "{name: '" + board.get("name").getAsString() + "',id: '" + board.get("id").getAsString() + "'}";
            sessionAttributes.put("CurrentBoard",json);
        }else responseText+=" "+Constants.INCORRECT_CREATION;

        responseText+=" ." + Constants.CONTINUE;


        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build();
    }
}
