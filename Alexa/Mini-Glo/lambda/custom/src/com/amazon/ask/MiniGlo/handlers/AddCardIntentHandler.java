package com.amazon.ask.MiniGlo.handlers;
import com.amazon.ask.MiniGlo.api.FunctionApi;
import com.amazon.ask.MiniGlo.model.Attributes;
import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.MiniGlo.utils.GloUtils;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

/*
* TODO:
* Ask for description of card
* Add information from extension web
* Add images
* Add screenshots
*
* */
public class AddCardIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AddCardIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String[] slotNames = {"cardName","columName","description"};
        String responseText = "", cardName = "", columnName = "", description="";
        Map<String,Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        Slot[] slotArray = new GloUtils().getSlotsArray(input);
        boolean allCorrect = true;

        if(slotArray==null) allCorrect= false;
        else{
            for(int i=0; i<slotArray.length; i++){
                for(int j=0; j<slotNames.length; j++){
                    if(slotArray[i].getName().equals(slotNames[j])){
                        switch(j){
                            case 0: cardName = slotArray[i].getValue();
                            break;
                            case 1: columnName = slotArray[i].getValue();
                            break;
                            case 2: description = slotArray[i].getValue();
                            break;
                        }
                    }
                }
            }
        }



        if(allCorrect){
            Object column = sessionAttributes.get(Attributes.COLUMN_NAME);
            if(column!=null) columnName = (String)column;
        }
        if(cardName==null) sessionAttributes.put("CARDNAME","false");
        else sessionAttributes.put("CARDNAME","true");

        sessionAttributes.put(Attributes.COLUMN_NAME,columnName);
        sessionAttributes.put(Attributes.CARD_NAME,cardName);

        if(allCorrect) allCorrect = new FunctionApi().addCardtoColumn(columnName,cardName,description);

        responseText = new GloUtils().getSpeechCon(allCorrect);


        if(allCorrect){ responseText += Constants.CORRECT_CREATION + ", "
                + sessionAttributes.get(Attributes.CARD_NAME) + " in "
                + sessionAttributes.get(Attributes.COLUMN_NAME);

            if(description!=null) responseText += " with description.";
        }

        else responseText += Constants.INCORRECT_CREATION;

        responseText += ". " +  Constants.CONTINUE;

        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build();
    }
}
