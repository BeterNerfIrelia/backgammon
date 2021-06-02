package fii.backgammon.server.client.commands;

import fii.backgammon.server.handlers.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class GetUserInfo implements Command{

    private static final String URI = "http://localhost:8081/users/";
    private static String url = URI;

    public GetUserInfo(String username) {
        url += username;
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        resetUrl();
        if(response.getStatusCode().is2xxSuccessful()) {
            return new StringBuilder("[")
                        .append(response.getBody().getId())
                        .append(",")
                        .append(response.getBody().getUsername())
                        .append("]")
                        .toString();
        }
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }

}
