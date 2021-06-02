package fii.backgammon.server.client.game.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dice {

    private List<Integer> dice = new ArrayList<>();
    private Random random = new Random();
    private Integer d1;
    private Integer d2;

    public List<Integer> roll() {
        dice.clear();

        d1 = random.nextInt(6) + 1;
        d2 = random.nextInt(6) + 1;
        if(d1 == d2) {
            dice = Collections.nCopies(4,d1);
        }
        else {
            dice.add(d1);
            dice.add(d2);
        }

        return dice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(dice.get(0));
        for(int i=1;i<dice.size();++i)
            sb.append(",").append(dice.get(i));
        sb.append("]");
        return sb.toString();
    }
}
