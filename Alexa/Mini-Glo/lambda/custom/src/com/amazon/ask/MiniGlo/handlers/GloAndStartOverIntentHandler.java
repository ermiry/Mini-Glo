package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.utils.GloUtils;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class GloAndStartOverIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("GloIntent")
        .and(Predicates.sessionAttribute(Attributes.STATE_KEY,Attributes.BOARD_STATE).negate()))
                ||input.matches(Predicates.intentName("AMAZON.StartOverIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        Map<String,Object> persistentAttributes = input.getAttributesManager().getPersistentAttributes();
        sessionAttributes.put(Attributes.STATE_KEY,Attributes.BOARD_STATE);
        persistentAttributes.put(Attributes.ENDSESSION,Attributes.CONTINUE);
        return GloUtils.startSession(input);
    }
}
