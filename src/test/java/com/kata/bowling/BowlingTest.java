package test.java.com.kata.bowling;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.google.common.collect.Lists;
import main.java.com.kata.bowling.Frame;
import main.java.com.kata.bowling.Game;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BowlingTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void shouldScore300() {
        for (int i = 0; i < 12; i++) {
            game.roll("X");
        }
        Pair<Integer, Frame[]> result = game.getScore(10);
        assertEquals(300, result.getKey().intValue());
        assertFramesScores(result, Lists.newArrayList(30, 60, 90, 120, 150, 180, 210, 240, 270, 300));
    }

    private void assertFramesScores(Pair<Integer, Frame[]> result, List<Integer> expectedScores) {
        for (int i = 0; i < expectedScores.size(); i++) {
            assertEquals(i + 1, result.getValue()[i].getFrame());
            assertEquals(expectedScores.get(i).intValue(), result.getValue()[i].getScore());
        }
    }

    @Test
    public void shouldScore() {
        for (int i = 0; i < 4; i++) {
            game.roll("3");
        }
        Pair<Integer, Frame[]> result = game.getScore(2);
        assertEquals(12, result.getKey().intValue());
        assertFramesScores(result, Lists.newArrayList(6, 12));

    }

    @Test
    public void shouldScore90() {
        for (int i = 0; i < 10; i++) {
            game.roll("9");
            game.roll("-");
        }
        Pair<Integer, Frame[]> result = game.getScore(10);
        assertEquals(90, result.getKey().intValue());
        assertFramesScores(result, Lists.newArrayList(9, 18, 27, 36, 45, 54, 63, 72, 81, 90));

    }

    @Test
    public void shouldScore150() {
        for (int i = 0; i < 10; i++) {
            game.roll("5");
            game.roll("/");
        }
        game.roll("5");
        Pair<Integer, Frame[]> result = game.getScore(10);
        assertEquals(150, result.getKey().intValue());
        assertFramesScores(result, Lists.newArrayList(15, 30, 45, 60, 75, 90, 105, 120, 135, 150));
    }

    @Test
    public void testCheckRollsWithInvalidSpare() throws Exception {
        game.roll("3");
        game.roll("3");
        for (int i = 0; i < 9; i++) {
            game.roll("/");
            game.roll("/");
        }
        int status = SystemLambda.catchSystemExit(() -> game.getScore(10));
        assertEquals(1, status);
    }

    @Test
    public void testCheckRollsWhichBegunWithSpare() throws Exception {
        game.roll("/");
        game.roll("3");
        for (int i = 0; i < 9; i++) {
            game.roll("/");
            game.roll("/");
        }
        int status = SystemLambda.catchSystemExit(() -> game.getScore(10));
        assertEquals(1, status);
    }

    @Test
    public void testCheckRollsWithInvalidFrame() throws Exception {
        game.roll("3");
        game.roll("3");
        for (int i = 0; i < 9; i++) {
            game.roll("9");
            game.roll("9");
        }
        int status = SystemLambda.catchSystemExit(() -> game.getScore(10));
        assertEquals(1, status);
    }

    @Test
    public void testCheckRollsWithInvalidRollsLength() throws Exception {
        game.roll("3");
        game.roll("3");
        for (int i = 0; i < 3; i++) {
            game.roll("9");
            game.roll("9");
        }
        int status = SystemLambda.catchSystemExit(() -> game.getScore(4));
        assertEquals(1, status);
    }

    @Test
    public void testCheckRollsWithInvalidFrames() throws Exception {
        game.roll("9");
        game.roll("-");
        game.roll("X");
        game.roll("X");
        game.roll("2");
        game.roll("/");
        game.roll("X");
        game.roll("X");
        game.roll("6");
        game.roll("6");
        game.roll("3");
        game.roll("3");
        game.roll("X");
        game.roll("2");
        game.roll("-");

        int status = SystemLambda.catchSystemExit(() -> game.getScore(10));
        assertEquals(1, status);
    }

    @Test
    public void testCheckRollsWithInvalidNumberOfFrames() throws Exception {
        game.roll("9");
        game.roll("-");
        game.roll("X");
        game.roll("X");
        game.roll("2");
        game.roll("/");
        game.roll("X");
        game.roll("X");
        game.roll("3");
        game.roll("3");
        game.roll("3");
        game.roll("3");
        game.roll("X");
        game.roll("2");
        game.roll("-");
        game.roll("2");

        int status = SystemLambda.catchSystemExit(() -> game.getScore(10));
        assertEquals(1, status);
    }


}
