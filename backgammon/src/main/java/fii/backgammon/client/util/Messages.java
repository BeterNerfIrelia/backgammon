package fii.backgammon.client.util;

public class Messages {

    private String message;

    public Messages() {
    }

    public Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void print() {
        System.out.println(this.message);
    }
}
