package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.MiniGlo.utils.GloUtils.getSessionAttributes;
import static com.amazon.ask.MiniGlo.utils.GloUtils.continueSession;

public class YesNoIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("YesIntent"))
        || input.matches(Predicates.intentName("NoIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String,Object> sessionAttributes = getSessionAttributes(input);


        if(input.matches(Predicates.intentName("YesIntent")))
            sessionAttributes.put(Attributes.ENDSESSION,Attributes.CONTINUE);
        else sessionAttributes.put(Attributes.ENDSESSION,Attributes.STOP);

        return continueSession(input);
    }
}
