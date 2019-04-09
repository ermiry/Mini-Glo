package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class ReconnectIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("ReconnectIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String accessToken = input.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();
        if(!FunctionApi.getSharedInstance().badAuthentication(accessToken,input).equals(Optional.empty())){
            accessToken = input.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();
            FunctionApi.getSharedInstance().disconnect();
            if(accessToken.equals(""))
                return input.getResponseBuilder()
                .withSpeech("It was impossible to reconnect, please go to the" +
                        "Alexa app and re-authenticate")
                .withLinkAccountCard()
                .withShouldEndSession(true)
                .build();
            else
                return input.getResponseBuilder()
                .withSpeech("Reconnected correctly")
                .withShouldEndSession(false)
                .build();

        }
        return input.getResponseBuilder()
                .withSpeech("You are already authenticated")
                .withShouldEndSession(false)
                .build();
    }
}
