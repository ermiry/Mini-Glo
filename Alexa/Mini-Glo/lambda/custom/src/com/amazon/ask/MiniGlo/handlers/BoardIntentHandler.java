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
import com.google.gson.JsonObject;

import javax.xml.bind.attachment.AttachmentMarshaller;
import java.text.AttributedCharacterIterator;
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
            String accessToken = sessionAttributes.get(Attributes.ACCESS_TOKEN).toString();
            String speechOutput;
            JsonObject board;
            IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
            Map<String, Slot> slots = intentRequest.getIntent().getSlots();
            Slot boardName = slots.get("boardName");

            boolean correct;
            correct =  (board = new FunctionApi().lookForBoard(boardName.getValue(),accessToken))!=null;
            speechOutput = new GloUtils().getSpeechCon(correct);

            if (correct) {
                sessionAttributes.put("CurrentBoard",board.toString());
                speechOutput += Constants.CORRECT_SHOW;
            } else speechOutput += Constants.INCORRECT_SHOW;

            speechOutput += ".  " + boardName.getValue();
            speechOutput += ". " + Constants.CONTINUE;

            if(new FunctionApi().badAuthentication())

            return input.getResponseBuilder()
                    .withSpeech(speechOutput)
                    .withReprompt(Constants.HELP_MESSAGE)
                    .withShouldEndSession(false)
                    .build();
    }

}

