package com.amazon.ask.MiniGlo;

import com.amazon.ask.MiniGlo.handlers.*;
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
                    new BoardIntentHandler(),
                    new AddColumnIntentHandler(),
                    new AddCardIntentHandler(),
                    new YesNoIntentHandler(),
                    new GloAndStartOverIntentHandler(),
                    new RepeatIntentHandler(),
                    new HelpIntentHandler(),
                    new ExitIntentHandler(),
                    new SessionEndedHandler()
                ).build());

    }

}
