package com.amazon.ask.MiniGlo.utils;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Map;
import java.util.Optional;

public class GloUtils {


    public static Optional<Response> startSession(HandlerInput input) {
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();

        if(sessionAttributes.get(Attributes.BOARD_NAME)==""){
            sessionAttributes.put(Attributes.RESPONSE_KEY, Constants.START_EDIT);
        }

        String speech = Attributes.RESPONSE_KEY + " ";

        return input.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }
}
