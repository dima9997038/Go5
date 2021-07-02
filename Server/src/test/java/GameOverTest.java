import org.junit.Test;

import static org.junit.Assert.*;

public class GameOverTest {
    private static int[][] grid = new int[10][10];


    @Test
    public void gameOverCheck() {
        String s ="0200000000212000000002001000000001000000000010000000000000000000000000000000000000000000000000000000";
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j]=Character.getNumericValue(s.charAt(count));
                count++;
            }
        }
        GameOver gameOver = new GameOver(grid,1);
        assertEquals(gameOver.gameOverCheck(grid,1),false);
        GameOver gameOver1 = new GameOver(grid,2);
        assertEquals(gameOver1.gameOverCheck(grid,2),false);
    }

    @Test
    public void gameOverCheck1() {
        String s="1111111111111221111111222111111122222112111122221211112222121112222212121222211211222221222122222022";
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j]=Character.getNumericValue(s.charAt(count));
                count++;
            }
        }
        GameOver gameOver = new GameOver(grid,1);
        assertEquals(gameOver.gameOverCheck(grid,1),true);
        GameOver gameOver1 = new GameOver(grid,2);
        assertEquals(gameOver1.gameOverCheck(grid,2),true);
    }
}