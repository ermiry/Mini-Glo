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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class BoardIntentHandler implements RequestHandler {

    private static final Random RANDOM = new Random();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("BoardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
            Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
            String responseText, speechOutput;

            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Slot boardName = null;
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();

            for (Slot slot : slots.values()) {
                if (slot != null) {
                    boardName = slot;
                    break;
                }
            }

            boolean correct;
            correct = new FunctionApi().lookForBoard(boardName.getValue());
            speechOutput = new GloUtils().getSpeechCon(correct);


            if (correct) {
                sessionAttributes.put(Attributes.BOARD_NAME, boardName.getValue());
                speechOutput += Constants.CORRECT_SHOW;
            } else speechOutput += Constants.INCORRECT_SHOW;

            speechOutput += ".  " + boardName.getValue();
            speechOutput += ". " + Constants.CONTINUE;

            return input.getResponseBuilder()
                    .withSpeech(speechOutput)
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(false)
                    .build();
    }

}

