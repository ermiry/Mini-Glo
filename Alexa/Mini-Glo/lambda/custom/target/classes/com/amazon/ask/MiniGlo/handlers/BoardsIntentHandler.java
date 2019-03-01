package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;
import java.util.function.Predicate;

import com.amazon.ask.MiniGlo.Functions;

public class BoardsIntentHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {
    private Functions functions = new Functions();
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("showBoardsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        boolean correct = functions.showAllBoards();
        if(correct) return input.getResponseBuilder()
                .withSpeech("Boards were shown correctly")
                .withShouldEndSession(true)
                .build();
        else return input.getResponseBuilder()
        .withSpeech("Boards were not shown")
        .withShouldEndSession(true)
        .build();
    }
}
