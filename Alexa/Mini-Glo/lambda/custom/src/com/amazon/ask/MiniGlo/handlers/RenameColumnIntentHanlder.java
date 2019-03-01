package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

public class RenameColumnIntentHanlder implements com.amazon.ask.dispatcher.request.handler.RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return false;
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return Optional.empty();
    }
}
