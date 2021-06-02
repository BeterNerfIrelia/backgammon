package fii.backgammon.server.client.commands;

import fii.backgammon.server.handlers.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class CreateLobby implements Command {

    private static final String URI = "http://localhost:8081/lobby/create";
    private static String url = URI;

    private User user;

    public CreateLobby(User user) {
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
        ResponseEntity<String> response = restTemplate.postForEntity(url,userMap, String.class);
        System.out.println(response);
        resetUrl();
        return response.getBody();
    }

    @Override
    public String getUri() {
        return null;
    }
}
