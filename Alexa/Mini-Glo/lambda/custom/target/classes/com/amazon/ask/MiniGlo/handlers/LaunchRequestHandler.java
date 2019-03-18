package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SimpleCard;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.MiniGlo.api.FunctionApi;

import java.util.Map;
import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        SimpleCard card = SimpleCard.builder().build();
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = new FunctionApi().getAccessToken();
        sessionAttributes.put(Attributes.STATE_KEY,Attributes.START_STATE);
        String reprompt = "";
        System.out.println("Access Token: " + accessToken);
        if(accessToken!=null) sessionAttributes.put("Access Token",accessToken);
        else sessionAttributes.put("Access Token","Null");

        if(accessToken!=null) reprompt  = Constants.HELP_MESSAGE;
        else
            reprompt = Constants.REGISTER_ACCOUNT;


        return input.getResponseBuilder()
                .withSpeech(Constants.WELCOME_MESSAGE + reprompt)
                .withReprompt(reprompt)
                .withShouldEndSession(false)
                .build();
    }
}
