package fii.backgammon.server.client.commands;

import fii.backgammon.server.handlers.entity.Game;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class UpdateGame implements Command{

    private static final String URI = "http://localhost:8081/game";
    private static String url = URI;

    private String body;

    public UpdateGame(String body) {
        this.body = body;
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();

        String[] tokens = body.split("\\+");

        Map<String,String> gameMap = new HashMap<>();
        gameMap.put("userId",tokens[0]);
        gameMap.put("board",tokens[1]);
        gameMap.put("dice",tokens[2]);
        gameMap.put("state",tokens[3]);

        HttpEntity<Map<String,String>> entity = new HttpEntity<>(gameMap);

        ResponseEntity<Game> response = restTemplate.exchange(url, HttpMethod.PUT,entity,Game.class);
        var responseBody = response.getBody();
        if(response.getStatusCode().is2xxSuccessful()) {
            System.out.println("RESPONSE BODY: " + responseBody.toString());
            return responseBody.toString();
        }

        return null;
    }

    @Override
    public String getUri() {
        return null;
    }
}
