package main.java.com.kata.bowling;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static main.java.com.kata.bowling.Utils.getIntValue;

public class Game {

    private String[] rolls = new String[21];
    private int rollIndex = 0;

    public void roll(String pins) {
        rolls[rollIndex++] = pins;
    }

    public Pair<Integer, Frame[]> getScore(int numberOfFrames) {
        mapRolls();
        checkRolls(rolls, rollIndex);
        checkFrames(rolls, rollIndex);
        int score = 0;
        int frameIndex = 0;
        int finalFrameRolls = 0;
        Frame frame;
        Frame[] framesScore = new Frame[10];
        for (int i = 0; i < numberOfFrames - 1; i++) {
            frame = new Frame();
            frame.setFrame(i + 1);
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
            frame.setScore(score);
            framesScore[i] = frame;
        }
        //Final frame
        frame = new Frame();
        frame.setFrame(numberOfFrames);
        if (isStrike(frameIndex)) {
            score += 10 + strike(frameIndex);
            frameIndex++;
            finalFrameRolls = 2;
        } else if (isSpare(frameIndex)) {
            score += 10 + spare(frameIndex);
            frameIndex += 2;
            finalFrameRolls = 1;
        } else {
            score += frameScore(frameIndex);
            frameIndex += 2;
        }
        frame.setScore(score);
        framesScore[numberOfFrames - 1] = frame;
        checkExtraRolls(frameIndex, finalFrameRolls);
        return Pair.of(score, framesScore);
    }

    private void mapRolls() {
        rolls = Arrays.stream(rolls).map(r -> {
            if (SpecialRoll.STRIKE.getName().equals(r))
                return "10";
            else if (SpecialRoll.MISS.getName().equals(r))
                return "0";
            return r;
        }).filter(Objects::nonNull).toArray(String[]::new);
    }

    private void checkExtraRolls(int frameIndex, int finalFrameRolls) {
        if (frameIndex < rollIndex - finalFrameRolls) {
            System.out.println("There still extra rolls; Invalid rolls");
            System.exit(1);

        }
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
