package com.amazon.ask.MiniGlo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import org.apache.logging.log4j.*;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;
import static org.apache.logging.log4j.LogManager.getLogger;


public class SessionEndedHandler implements RequestHandler {
    private static Logger LOG = getLogger(SessionEndedHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(SessionEndedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        SessionEndedRequest sessionEndedRequest = (SessionEndedRequest) input.getRequestEnvelope().getRequest();
        LOG.debug("Session ended with reason:" + sessionEndedRequest.getReason().toString());
        return Optional.empty();
    }
}
