package com.amazon.ask.MiniGlo;

import com.amazon.ask.MiniGlo.handlers.*;
import com.amazon.ask.MiniGlo.handlers.helpIntents.HelpTypeIntentHandler;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;

/**
 * TODO:
 *
 * */
public class MiniGloSkillStreamHandler extends SkillStreamHandler
{
    public MiniGloSkillStreamHandler(){
        super(Skills.standard()
                .addRequestHandlers(
                    new LaunchRequestHandler(),
                    new OpenBoardIntentHandler(),
                    new AddColumnIntentHandler(),
                    new AddCardIntentHandler(),
                    new EditCardIntentHandler(),
                    new YesNoIntentHandler(),
                    new CreateBoardIntentHandler(),
                    new DeleteBoardIntentHandler(),
                    new ReconnectIntentHandler(),
                    new EditColumnIntentHandler(),
                    new EditBoardIntentHandler(),
                    new DeleteColumnIntentHandler(),
                    new CreateCommentIntentHandler(),
                    new DeleteCardIntentHandler(),
                    new HelpTypeIntentHandler(),
                    new GloAndStartOverIntentHandler(),
                    new RepeatIntentHandler(),
                    new HelpIntentHandler(),
                    new ExitIntentHandler(),
                    new SessionEndedHandler()
                ).build());

    }

}
