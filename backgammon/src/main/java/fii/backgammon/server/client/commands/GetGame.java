package fii.backgammon.server.client.commands;

import fii.backgammon.server.handlers.entity.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GetGame implements Command{

    private static final String URI = "http://localhost:8081/game/";
    private static String url = URI;

    public GetGame(String username) {
        url += username;
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Game> response = restTemplate.getForEntity(url,Game.class);
        resetUrl();
        if(response.getStatusCode().is2xxSuccessful()) {
            StringBuilder sb = new StringBuilder(response.getBody().getUserId()).append("+")
                                    .append(response.getBody().getBoard()).append("+")
                                    .append(response.getBody().getDice()).append("+")
                                    .append(response.getBody().getState());
            return sb.toString();
        }
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }
}
