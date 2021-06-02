package fii.backgammon.server.commands;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class RegisterCommand implements Command{

    private static final String URI = "http://localhost:8081/users/username?username=";
    private static String uri = "http://localhost:8081/users/username?username=";

    public RegisterCommand(String username) {
        uri += username;
    }

    public void resetUri() {
        uri = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        return null;
    }

    @Override
    public String getUri() {
        return uri;
    }
}
