package ro.andreistoian.SpringWebSockets.client;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

@Slf4j
public class Client {

    private static final String url = "http://localhost:8080/login";
    private static final String stompURL = "ws://localhost:8080/chat";

    public static void main(String[] args) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user-name","andrei");
        map.add("user-pass", "pass");
        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(map, headers);
        ResponseEntity<String> response =
                new RestTemplate().postForEntity(url, entity, String.class);
        log.info("Response status code : " + response.getStatusCode().toString());
        WebSocketStompClient client = new WebSocketStompClient(new StandardWebSocketClient());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        client.setMessageConverter(new MappingJackson2MessageConverter());
        client.connect(stompURL, sessionHandler);

        System.out.println("Type something and press any key to exit...");
        new Scanner(System.in).nextLine();


    }

}
