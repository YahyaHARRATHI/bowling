package main.java.com.kata.bowling;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;
import static main.java.com.kata.bowling.Utils.getIntValue;

public class Game {

    private String[] rolls = new String[21];
    private int rollIndex = 0;

    public void roll(String pins) {
        rolls[rollIndex++] = pins;
    }

    public int getScore() {
        rolls = Arrays.stream(rolls).map(r -> {
            if (SpecialRoll.STRIKE.getName().equals(r))
                return "10";
            else if (SpecialRoll.MISS.getName().equals(r))
                return "0";
            return r;
        }).toArray(String[]::new);
        checkRolls(rolls, rollIndex);
        checkFrames(rolls, rollIndex);
        int score = 0;
        int frameIndex = 0;

        for (int i = 0; i < 10; i++) {
            if (isStrike(frameIndex)) {
                score += 10 + strike(frameIndex);
                frameIndex++;
            } else if (isSpare(frameIndex)) {
                score += 10 + spare(frameIndex);
                frameIndex += 2;
            } else {
                score += frameScore(frameIndex);
                frameIndex += 2;
            }
        }
        return score;
    }

    private void checkFrames(String[] rolls, int rollIndex) {
        for (int i = 0; i < rollIndex; i++) {
            String roll = rolls[i];
            if (getIntValue(roll) != 10) {
                if (i + 1 < rollIndex && Utils.isNumeric(rolls[i + 1]) &&
                        Utils.getIntValue(roll) + Utils.getIntValue(rolls[i + 1]) > 10) {
                    System.out.println("Invalid frame at index " + i + " with values : " + roll + ", " + rolls[i + 1]);
                    System.exit(1);
                }
                i++;
            }
        }
    }

    private void checkRolls(String[] rolls, int rollIndex) {
        if (rollIndex < 11) {
            System.out.println("Min number of rolls is 11");
            System.exit(1);
        }
        for (int i = 0; i < rollIndex; i++) {
            String roll = rolls[i];
            if (i == 0 && SpecialRoll.SPARE.getName().equals(rolls[0])) {
                System.out.println("Invalid roll at inedx " + i + "with value " + roll);
                System.exit(1);
            }
            // Allowed range : [0 - 10 ]
            if (Utils.isNumeric(roll) && (getIntValue(roll) > 10 || getIntValue(roll) < 0)) {
                System.out.println("Invalid roll at index " + i + "with value " + roll);
                System.exit(1);
            }
            // Allowed values : X , / , -
            if (!Utils.isNumeric(roll) &&
                    !Arrays.stream(SpecialRoll.values()).map(SpecialRoll::getName).collect(toList()).contains(roll)) {
                System.out.println("Invalid roll at index " + i + "with value " + roll);
                System.exit(1);
            }
            // Check spares
            if (i + 1 < rollIndex && SpecialRoll.SPARE.getName().equals(roll) && SpecialRoll.SPARE.getName().equals(rolls[i + 1])) {
                System.out.println("Invalid consecutive spares " + i + ", " + (i + 1) + " With values :" + roll + ",  " + rolls[i + 1]);
                System.exit(1);
            }
        }
    }

    private boolean isSpare(int frameIndex) {
        return SpecialRoll.SPARE.getName().equals(rolls[frameIndex + 1]) &&
                getIntValue(rolls[frameIndex + 1]) < 10;
    }

    private boolean isStrike(int frameIndex) {
        return getIntValue(rolls[frameIndex]) == 10;
    }

    private int spare(int frameIndex) {
        return getIntValue(rolls[frameIndex + 2]);
    }

    private int strike(int frameIndex) {
        return getIntValue(rolls[frameIndex + 1]) + getIntValue(rolls[frameIndex + 2]);
    }

    private int frameScore(int frameIndex) {
        return getIntValue(rolls[frameIndex]) + getIntValue(rolls[frameIndex + 1]);
    }
}
