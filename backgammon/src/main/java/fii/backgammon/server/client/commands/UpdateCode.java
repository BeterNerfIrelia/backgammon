package fii.backgammon.server.client.commands;

import org.springframework.web.client.RestTemplate;

public class UpdateCode implements Command{

    private static final String URI = "http://localhost:8081/lobby/";
    private static String url = URI;

    public UpdateCode(String username) {
        url += username += "/code?code=on";
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url,null);

        resetUrl();
        return "200";
    }

    @Override
    public String getUri() {
        return null;
    }
}
