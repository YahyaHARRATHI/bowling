package main.java.com.kata.bowling;

public enum SpecialRoll {
    STRIKE("X"),
    SPARE("/"),
    MISS("-");
    String name;

    SpecialRoll(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String score) {
        this.name = score;
    }
}
