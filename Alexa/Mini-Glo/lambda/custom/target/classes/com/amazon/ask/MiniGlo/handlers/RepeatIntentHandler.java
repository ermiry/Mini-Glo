package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class RepeatIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.RepeatIntent")
        .and(Predicates.sessionAttribute(Attributes.STATE_KEY,Attributes.BOARD_STATE)));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String speech = "Waiting for tasks";
        return input.getResponseBuilder()
                .withShouldEndSession(false)
                .withSpeech(speech)
                .withSpeech(speech)
                .build();
    }
}
