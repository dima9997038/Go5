public class GameOver {
    public static int [] [] copy_grid;
    public static int[][] visit=new int[10][10];
    public static int col_start;
    public static int row_start;
    public static boolean circle=false;
    public static int color=2;


    public GameOver(int [][] gridCirce, int color) {
        copy_grid=cloneArray(gridCirce) ;
  //      gameOver(copy_grid,color) ;


    }

    public boolean gameOverCheck(int[][] copy_grid, int color) {
        boolean res=false;
        for (int col = 0; col <10 ; col++) {
            for (int row = 0; row <10 ; row++) {

                if (copy_grid[col][row]==0)
                {
   //                 System.out.println("col="+col+ "  row="+row);
                    int[][] mygrid = cloneArray(copy_grid);
                    mygrid[col][row] = color;
                    String s = getGridCopy(mygrid);
                    mygrid =cloneArray(deleteBols(mygrid)) ;
                    String s1=(getGridCopy(mygrid));
                    if (s.equals(s1)) {
                        res = res || s.equals(s1);
   //                     System.out.println("gameomer "+ res);
                    }

                }

            }

        }
        System.out.println("not gameomer "+ res);
        return res;
    }


    public String getGridCopy(int [][] arr){
        StringBuilder res= new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                res.append(arr[i][j]);
            }
        }
        return res.toString();
    }

    public  int [] [] deleteBols(int [][] arr){
        String res;
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j < 10; j++) {
                int color=arr[i][j];
                if (color!=0) {
                    col_start = i;
                    row_start = j;
                    dfs(i, j,color, arr);
                }
      //          System.out.println("Visit");
      //          input(visit);
       //         System.out.println("Grid");
      //          input(grid);

                if (circle) {
                    Grid.delete_have_neighbor_zero(visit);
                    Grid.delete(arr,visit);
  //                  System.out.println("Delete");
                    }
                circle=false;

                visit=new int[10][10];
            }
        }

   //     System.out.println("final");
  //      input(arr);

        return arr;
    }




    private void dfs(int col, int row, int color , int[][] arr) {


        if (arr[col][row]==0) return;
        if (arr[col][row]!=color) { visit[col][row]=9; return;}


        visit[col][row]=1;
  //      System.out.println("in " + col+" "+row);
        if (col+1<10){if (visit[col+1][row]==0) dfs(col+1,row,color,arr);}
        if ((col+1)<10&&(row+1)<10){ if (visit[col+1][row+1]==0) dfs(col+1,row+1,color,arr); }
        if ((col+1)<10&&(row-1)>=0) {if (visit[col+1][row-1]==0) dfs(col+1,row-1,color,arr);}
        if (row+1<10){ if (visit[col][row+1]==0) dfs(col,row+1,color,arr);}
        if (row-1>=0){if (visit[col][row-1]==0) dfs(col,row-1,color,arr); }
        if (col-1>=0){if (visit[col-1][row]==0) dfs(col-1,row,color,arr); }
        if ((col-1)>=0&&(row+1)<5) {if (visit[col-1][row+1]==0) dfs(col-1,row+1,color,arr);}
        if ((col-1)>=0&&(row-1)>=0){ if (visit[col-1][row-1]==0) dfs(col-1,row-1,color,arr);}


        visit[col][row]=2;
  //      System.out.println("out " + col+" "+row);
        if ((col_start==col)&&(row_start==row)){
 //           System.out.println("Circle enable");
            circle=true;
        }
    }


    private  void input(int[][] grid) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <10 ; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println("---------------");
    }
    public  int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

}
