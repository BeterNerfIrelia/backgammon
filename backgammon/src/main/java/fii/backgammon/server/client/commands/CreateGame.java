package fii.backgammon.server.client.commands;

import fii.backgammon.server.handlers.entity.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CreateGame implements Command {

    private static final String URI = "http://localhost:8081/game/create";
    private static String url = URI;

    private String userId;
    private String board;
    private String dice;
    private String state;

    public CreateGame(String userId, String board, String dice, String state) {
        this.userId = userId;
        this.board = board;
        this.dice = dice;
        this.state = state;
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        resetUrl();

        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> gameMap = new HashMap<>();
        gameMap.put("userId",this.userId);
        gameMap.put("board",this.board);
        gameMap.put("dice",this.dice);
        gameMap.put("state",this.state);

        ResponseEntity<Game> response = restTemplate.postForEntity(url,gameMap,Game.class);

        System.out.println(response);

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
