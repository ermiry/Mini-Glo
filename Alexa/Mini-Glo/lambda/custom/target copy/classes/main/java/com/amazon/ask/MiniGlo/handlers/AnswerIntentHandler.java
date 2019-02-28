package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import static com.amazon.ask.request.Predicates.sessionAttribute;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

public class AnswerIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AnswerIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        String responseText;
        String speechOutput = " ";
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        for(Slot slot:slots.values()){
            if(slot!=null){
                speechOutput = slot.getValue();
            }
        }
        return input.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(true)
                .build();
    }
}
