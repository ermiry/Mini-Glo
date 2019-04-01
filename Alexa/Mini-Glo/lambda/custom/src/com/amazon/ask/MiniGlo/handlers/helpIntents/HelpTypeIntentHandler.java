package com.amazon.ask.MiniGlo.handlers.helpIntents;

import com.amazon.ask.MiniGlo.model.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class HelpTypeIntentHandler  implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("HelpTypeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        String responseText="";
        try{
            switch(slots.get("type").getValue().toUpperCase()){
                    case ("BOARDS"):
                    case ("BOARD"):
                        switch(slots.get("action").getValue().toUpperCase()){
                            case "OPEN":
                                responseText= "For Boards, to open you can say." +
                                        "open board (and the name), or. search board (and the name), or. show board(and the name)";
                                break;
                            case "CREATE":
                                responseText = "For Boards, to create you can say. " +
                                        "create board called (and the name). or. " +
                                        "create board with name (and the name)";
                                break;
                            case "EDIT":
                                responseText = "For Boards, to edit name you can say. " +
                                        "change name of board (and the name) to (new name). or. "+
                                        "change board (and the name) name to (new name)";
                                break;
                            case "DELETE":
                                responseText = "For Boards, to delete you can say." +
                                        "delete current board. or. delete board (and the board Name)";
                                break;
                        }

                        break;

                    case "COLUMNS":
                    case "COLUMN":
                        switch(slots.get("action").getValue().toUpperCase()){
                            case "CREATE":
                                responseText = "For Columns, to create you can say. " +
                                        "add column with name (and the name). or. " +
                                        "Create column called (and the name). or. " +
                                        "add column called  (and the name).";
                                break;
                            case "EDIT":
                                responseText = "For Columns, to edit name you can say. " +
                                        "edit column (and the name) changing name to (new name). or. "+
                                        "change column (and the name),current name to (new name) ";
                                break;
                            case "DELETE":
                                responseText = "For Columns, to delete you can say." +
                                        "delete columns (and the column Name)";
                                break;
                        }
                        break;

                    case "CARDS":
                    case "CARD":
                        switch(slots.get("action").getValue().toUpperCase()){
                            case "CREATE":
                                responseText = "For Cards, to create you can say. " +
                                        "add card (and the name) in current column. or. " +
                                        "add card (and the name) to column (and the column name). or. " +
                                        "create card (and the name) in column (and the column name) with description (and the description). or ";
                                break;
                            case "EDIT":
                                responseText = "For Cards, to edit you can say. " +
                                        "Add description (and the description) to card called (and the name). or "+
                                        "Add label (and the label name) to card called (and the name). or. " +
                                        "Add due date (and the date) to card called (and the name). or. " +
                                        "Change due date of card called (and the name) for (and the date). or. " +
                                        "Remove label (and the label name) of card called (and the name). or. " +
                                        "Change description of card called (and the name) for (and the description)";
                                break;
                            case "DELETE":
                                responseText = "For Cards, to delete you can say. " +
                                        "delete (and the name)  card. or. delete card called(and the name)";
                                break;
                        }
                        break;
            }
        }catch(NullPointerException e){
            responseText="An error ocurred while searching for help, please try again";
            e.printStackTrace();
        }

        return input.getResponseBuilder()
                .withSpeech(responseText)
                .withReprompt(Constants.HELP_MESSAGE)
                .withShouldEndSession(false)
                .build();

    }
}
