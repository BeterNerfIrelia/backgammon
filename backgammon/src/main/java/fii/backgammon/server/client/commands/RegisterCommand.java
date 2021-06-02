package fii.backgammon.server.client.commands;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RegisterCommand implements Command{

    private static final String URI = "http://localhost:8081/users/username?username=";
    private static String url = URI;

    public RegisterCommand(String username) {
        url += username;
    }

    public void resetUrl() {
        url = URI;
    }

    @Override
    public String run() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,entity, String.class);
        resetUrl();
        if(response.getStatusCode().is2xxSuccessful())
            return response.getBody();
        if(response.getStatusCode().is3xxRedirection())
            return  response.getBody();
        return null;
    }

    @Override
    public String getUri() {
        return URI;
    }
}
