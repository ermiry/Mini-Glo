package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

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
        String responseText;
        String speechOutput;
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        String columnName = slots.get("columnName").getValue();

        speechOutput = "I add column " + columnName + "to board";

        return input.getResponseBuilder()
                .withSpeech(speechOutput)
                .withShouldEndSession(true)
                .build();
    }
}
