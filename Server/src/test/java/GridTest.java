import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GridTest {

    @Test
    public void deleteBols() {  // check delete bols
 //       Grid gridelement=new Grid();
        String s="0111111000111201000011111100001110100222111210200012202002220202122020020212122000202012200000001100";
  //      String s="0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        char[] ch=s.toCharArray();
        Grid.grid=new int[10][10];
  //      int [][]arr=new int[10][10];
        boolean res= true;
        int count=0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Grid.grid[i][j]=Character.getNumericValue(ch[count]) ;
  //              arr[i][j]=ch[count];
                count++;
            }

        }
 //       Grid.input(Grid.grid);
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j <10 ; j++) {
//                System.out.print(Grid.grid[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println("---------------");
        Grid.deleteBols();
        System.out.println(Grid.getGrid().toString());
        String result="0111111000111201000011111100001110100222111210200012202002220202022020020202122000202012200000001100";

        assertEquals(Grid.getGrid().toString(),result);
    }

    @Test
    public void deleteBols1() { // check collapsestep
        String s="1110000000121000000001000000000000000000000000000000000000000000000000000000000000022220000000000000";
        char[] ch=s.toCharArray();
        System.out.println("count "+Grid.count_delete_white+":"+Grid.count_delete_black );

        Grid.grid=new int[10][10];
        int count=0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Grid.grid[i][j]=Character.getNumericValue(ch[count]) ;
                count++;
            }

        }
        Grid.addrowindex=1;
        Grid.addcolindex=1;
        Grid.deleteBols();
        System.out.println("count "+Grid.count_delete_white+":"+Grid.count_delete_black );
        System.out.println(Grid.collapsestep);
        assertEquals(Grid.collapsestep,true);

    }

    @Test
    public void deleteBols2() {
 //       String s="0200000000212000000002001000000001000000000010000000000000000000000000000000000000000000000000000000";
        String s="0000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
           char[] ch=s.toCharArray();
        Grid.grid=new int[10][10];
        //      int [][]arr=new int[10][10];
        boolean res= true;
        int count=0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Grid.grid[i][j]=Character.getNumericValue(ch[count]) ;
                //              arr[i][j]=ch[count];
                count++;
            }

        }

  //      Grid.deleteBols();
 //       System.out.println(Grid.getGrid().toString());
 //       String result="0111111000111201000011111100001110100222111210200012202002220202022020020202122000202012200000001100";

        GameOver gameOver1=new GameOver(Grid.grid,1);
        GameOver gameOver2=new GameOver(Grid.grid,2);
        assertEquals(gameOver1.gameOverCheck(Grid.grid,1),false);
       assertEquals(gameOver2.gameOverCheck(Grid.grid,2),false);

    }
}