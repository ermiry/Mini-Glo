package com.GitHub.Glo;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.Random;

public class InstructionApiHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler {


    @Override
    public boolean canHandle(HandlerInput handlerInput) {

        return true;
    }



    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String responseSpeech;
        //IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
        //System.out.println(intentRequest.getIntent().getSlots());
        return handlerInput.getResponseBuilder().withSpeech("hola").build();
    }
}
