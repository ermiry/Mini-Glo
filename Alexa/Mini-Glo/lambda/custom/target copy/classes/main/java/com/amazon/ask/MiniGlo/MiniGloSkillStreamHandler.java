package com.amazon.ask.MiniGlo;

import com.amazon.ask.MiniGlo.handlers.*;
import com.amazon.ask.Skills;
import com.amazon.ask.SkillStreamHandler;


public class MiniGloSkillStreamHandler extends SkillStreamHandler
{
    public MiniGloSkillStreamHandler(){
        super(Skills.standard()
            .addRequestHandlers(new AnswerIntentHandler(),
                    new LaunchRequestHandler(),
                    new HelpIntentHandler(),
                    new ExitSkillHandler(),
                    new SessionEndedHandler()).build());
    }

}
