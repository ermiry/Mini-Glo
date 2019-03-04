package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BoardIntentHandler implements RequestHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random RANDOM = new Random();
    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(Predicates.intentName("BoardIntent")
                .and(Predicates.sessionAttribute(Attributes.STATE_KEY,Attributes.BOARD_STATE))
        );
    }

    @Override
    public Optional<Response> handle(HandlerInput input) { String speechOutput;
        String responseText;
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        String boardName = intentRequest.getIntent().getSlots().get("boardName").getValue();
        boolean correct = new FunctionApi().lookForBoard(boardName);
        if(correct) {
            responseText = getSpeechCon(correct);
            responseText += Constants.CORRECT_SHOW;
        }else{
            responseText = getSpeechCon(correct);
            responseText += Constants.INCORRECT_SHOW;
        }
        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withShouldEndSession(false)
                .build();


    }

    private String getSpeechCon(boolean b) {
        if(b) return "<say-as interpret-as='interjection'>" + getRandomItem(Constants.CORRECT_RESPONSES) + "!</say-as><break strength='strong'/>";
        else return "<say-as interpret as='interjection'>" + getRandomItem(Constants.INCORRECT_RESPONSES) + "!</say-as><break strength='strong'/>";
    }

    private <T> T getRandomItem(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}