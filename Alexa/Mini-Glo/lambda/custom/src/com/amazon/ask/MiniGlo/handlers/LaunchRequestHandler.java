package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = input.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();
        sessionAttributes.put(Attributes.STATE_KEY,Attributes.START_STATE);
        String reprompt;
        System.out.println("Access Token: " + accessToken);
        if(accessToken!=null) sessionAttributes.put(Attributes.ACCESS_TOKEN,accessToken);
        else sessionAttributes.put(Attributes.ACCESS_TOKEN,"null");

        if(accessToken!=null){
            reprompt  = Constants.HELP_MESSAGE;
            return input.getResponseBuilder()
                    .withSpeech(Constants.WELCOME_MESSAGE + reprompt)
                    .withReprompt(reprompt)
                    .withShouldEndSession(false)
                    .build();
        }
        else {
            return input.getResponseBuilder()
                    .withSpeech(Constants.WELCOME_MESSAGE + " " + "You arent registered to mini-glo. " +
                            "Please see Alexa app to log in")
                    .withLinkAccountCard()
                   .build();
        }

    }
}
