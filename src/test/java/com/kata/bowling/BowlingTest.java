package test.java.com.kata.bowling;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import main.java.com.kata.bowling.Game;
import org.junit.Before;
import org.junit.Test;

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
        assertEquals(300, game.getScore());
    }

    @Test
    public void shouldScore90() {
        for (int i = 0; i < 10; i++) {
            game.roll("9");
            game.roll("-");
        }
        assertEquals(90, game.getScore());
    }

    @Test
    public void shouldScore150() {
        for (int i = 0; i < 10; i++) {
            game.roll("5");
            game.roll("/");
        }
        game.roll("5");
        assertEquals(150, game.getScore());
    }

    @Test
    public void testCheckRollsWithInvalidSpare() throws Exception {
        game.roll("3");
        game.roll("3");
        for (int i = 0; i < 9; i++) {
            game.roll("/");
            game.roll("/");
        }
        int status = SystemLambda.catchSystemExit(() -> game.getScore());
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
        int status = SystemLambda.catchSystemExit(() -> game.getScore());
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
        int status = SystemLambda.catchSystemExit(() -> game.getScore());
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
        int status = SystemLambda.catchSystemExit(() -> game.getScore());
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

        int status = SystemLambda.catchSystemExit(() -> game.getScore());
        assertEquals(1, status);
    }


}
