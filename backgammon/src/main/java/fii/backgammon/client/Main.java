package fii.backgammon.client;

import fii.backgammon.client.util.stages.Register;

public class Main {
    public static void main(String[] args) {

            Client client = new Client();
            client.connect();
            Register.run();
            client.disconnect();

    }
}
