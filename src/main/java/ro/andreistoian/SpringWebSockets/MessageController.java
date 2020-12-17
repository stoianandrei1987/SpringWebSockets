package ro.andreistoian.SpringWebSockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/cm")
    public ServerResponseMessage respond(@Payload Message message, Principal principal) throws Exception {
        if(true) {
            if(principal!=null)
            log.info("principal is : " + principal.getName() + " with message : " + message.
                    getText().substring(0, 5) + "..." );

            ServerResponseMessage resp = new ServerResponseMessage(message.getName() + ": " + message.getText());
            return resp;

        } else throw new IllegalAccessException("Can not post messages without being logged in!");
    }
}
