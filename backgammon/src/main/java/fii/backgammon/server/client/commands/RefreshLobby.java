package fii.backgammon.server.client.commands;


import fii.backgammon.server.handlers.entity.Lobby;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

public class RefreshLobby implements Command{

    private static final String URI = "http://localhost:8081/lobby/";
    private static String url = URI;

    private String username;

    public RefreshLobby(String username) {
        this.username = username;
        url += username;

    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Lobby> response = restTemplate.getForEntity(url,Lobby.class);
        resetUrl();
        if(response.getStatusCode().is2xxSuccessful()) {
        Lobby lobby = response.getBody();
        StringBuilder res = new StringBuilder("[").append(lobby.getUser1().getId()).
                                 append(",").append(lobby.getUser1().getUsername());
        if(lobby.getUser2() == null) {
            res.append(",").append(lobby.getCode()).append("]");
        }
        else {
            res.append(",").append(lobby.getUser2().getId()).append(",").
                            append(lobby.getUser2().getUsername()).append(",").
                            append(lobby.getCode()).append("]");
            }

        return res.toString();
        }

        return null;
    }

    @Override
    public String getUri() {
        return null;
    }
}
