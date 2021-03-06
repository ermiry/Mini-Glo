package com.amazon.ask.MiniGlo.utils;

import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class GloUtils {
    private GloUtils sharedInstance;

    public GloUtils getGloUtils(){
        if(sharedInstance==null) sharedInstance = new GloUtils();
        return sharedInstance;
    }

    private static final Random RANDOM = new Random();

    public static Optional<Response> startSession(HandlerInput input) {
        Map<String,Object> sessionAttributes =getSessionAttributes(input);
        if(sessionAttributes.get(Attributes.CURRENT_BOARD)==""){
            sessionAttributes.put(Attributes.RESPONSE_KEY, Constants.START_EDIT);
        }

        String speech = Attributes.RESPONSE_KEY + " ";

        return input.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }

    public static Optional<Response> continueSession(HandlerInput input){
        String responseText;
        Map<String,Object> sessionAttributes = getSessionAttributes(input);
        boolean stop = false;
        if(sessionAttributes.get(Attributes.ENDSESSION).equals(Attributes.CONTINUE)) responseText = "Ok, How can I help you";
        else {
            responseText = "That was all from me, please let me know if you need help";
            stop = true;
        }

        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withShouldEndSession(stop).build();
    }

    public  String getSpeechCon(boolean b) {
        if (b) {
            return "<say-as interpret-as='interjection'>" + getRandomItem(Constants.CORRECT_RESPONSES) + "! </say-as><break strength='strong'/>";
        } else {
            return "<say-as interpret-as='interjection'>" + getRandomItem(Constants.INCORRECT_RESPONSES) + "</say-as><break strength='strong'/>";
        }
    }

    private static <T> T getRandomItem(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static Map<String,Object> getSessionAttributes(HandlerInput input){
        return input.getAttributesManager().getSessionAttributes();
    }
}

