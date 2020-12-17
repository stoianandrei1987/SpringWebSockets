package ro.andreistoian.SpringWebSockets.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import ro.andreistoian.SpringWebSockets.Message;
import ro.andreistoian.SpringWebSockets.ServerResponseMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/cm", this);
        logger.info("Subscribed to /topic/cm");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String messageAsText = "{'name' : 'Andrei', 'message' : 'Howdy!'}";

        session.send("/app/hello", getSampleMessage());
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
                                Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ServerResponseMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

        logger.info("receiveed something :" + ((ServerResponseMessage)payload).getContent());

        //logger.info("Received : " + ((ServerResponseMessage) payload).getContent());
    }


    private Message getSampleMessage() {
        Message msg = new Message();
        msg.setName("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }
}
