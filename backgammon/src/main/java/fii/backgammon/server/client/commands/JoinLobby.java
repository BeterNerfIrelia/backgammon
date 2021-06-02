package fii.backgammon.server.client.commands;

import fii.backgammon.server.handlers.entity.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class JoinLobby implements Command {

    private static final String URI = "http://localhost:8081/lobby/";
    private static String url = URI;

    User user;

    public JoinLobby(String username, User user) {
        url += username;
        this.user = user;
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String,String> userMap = new HashMap<>();
        userMap.put("id",user.getId());
        userMap.put("username",user.getUsername());

        HttpEntity<Map<String,String>> entity = new HttpEntity<>(userMap);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,entity,String.class);

        resetUrl();
        if(response.getStatusCode().is2xxSuccessful()) {
            System.out.println(response.getBody());
            return response.getBody();
        }
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }
}
