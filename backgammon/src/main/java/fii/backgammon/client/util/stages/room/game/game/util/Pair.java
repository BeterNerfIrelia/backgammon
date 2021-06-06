package fii.backgammon.client.util.stages.room.game.game.util;

public class Pair<K,V> {
    private K first;
    private V second;

    public Pair() {
    }

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public Pair(fii.backgammon.server.client.game.util.Pair<K,V> pair) {
        this.first = pair.getFirst();
        this.second = pair.getSecond();
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public Pair<K, V> getPair() {
        return new Pair<>(this.getFirst(),this.getSecond());
    }

    public void setPair(Pair<K,V> pair) {
        this.first = pair.getFirst();
        this.second = pair.getSecond();
    }

    public void setPair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
