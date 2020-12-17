package ro.andreistoian.SpringWebSockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("topic/cm")
    public ServerResponseMessage respond(@Payload Message message) throws Exception {
        log.info("message come from : " + message.getName());
        log.info("message contents : " + message.getText());

        ServerResponseMessage resp = new ServerResponseMessage(message.getName() + ": " + message.getText());
        return resp;

    }
}
