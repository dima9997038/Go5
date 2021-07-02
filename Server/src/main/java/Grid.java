public class Grid {

    public static int[][] grid = new int[10][10];
    public static int turn = 1;
    public static int count_delete_white=0;
    public static int count_delete_black=0;

    public static int addcolindex=-1;
    public static int addrowindex=-1;
    public static     boolean collapsestep=false;



    public static String addPosition(String col, String row, ClientHandler clientHandler){
        int colindex = Integer.parseInt(col);
        int rowindex = Integer.parseInt(row);
        addcolindex=colindex;
        addrowindex=rowindex;
        StringBuilder res;

        if (turn == Server.players.get(clientHandler)) {
            if (grid[colindex][rowindex]==0){
                grid[colindex][rowindex] = Server.players.get(clientHandler);
                System.out.println(Server.players.get(clientHandler));
                turn = (turn == 2) ? 1 : 2;
            }


        }
        res = getGrid();
        return res.toString();
    }
    public static StringBuilder getGrid(){
        StringBuilder res= new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                res.append(grid[i][j]);
            }
        }
        return res;

    }

    public static void emptyArray(){
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                grid[i][j] = 0;
            }
        }
    }

    public static int[][] visit=new int[10][10];
    public static int col_start;
    public static int row_start;
    public static boolean circle=false;

    public static String deleteBols(){
        StringBuilder res;
        collapsestep=false;
        int [][] clone_grid=cloneArray(grid);
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j < 10; j++) {
                int color=grid[i][j];
                if (color!=0) {
                    col_start = i;
                    row_start = j;
                    dfs(i, j,color);
                }
                    delete_have_neighbor_zero(visit);
                    delete_have_neighbor_zero(visit);
                    delete_have_neighbor_zero(visit);
                    delete_have_neighbor_zero(visit);
                    delete (grid,visit);
                circle=false;
                visit=new int[10][10];
            }
        }

//        System.out.println("final");
//        input(grid);

        res = getGrid();
        if (addcolindex!=-1&&addrowindex!=-1){
            if ((grid[addcolindex][addrowindex]==0)&&(clone_grid[addcolindex][addrowindex]!=0)) collapsestep=true;
        }
        if (collapsestep){
            grid=cloneArray(clone_grid);
        }
        checkpoint(grid,clone_grid); // count score
        addrowindex=-1;
        addcolindex=-1;
        return res.toString();
    }
 // count score
    private static void checkpoint(int[][] grid, int[][] clone_grid) {
        for (int col = 0; col <10 ; col++) {
            for (int row = 0; row <10 ; row++) {
                if (grid[col][row]!=clone_grid[col][row]) {
                    if (clone_grid[col][row] == 1) count_delete_black++;
                    if (clone_grid[col][row] == 2) count_delete_white++;
                }
            }

        }
    }

    public static void delete_have_neighbor_zero(int[][] visit) {
        for (int col = 0; col <10 ; col++) {
            for (int row = 0; row <10 ; row++) {
                if (visit[col][row]==9) {
                    if (col + 1 < 10) {
                        if (visit[col + 1][row] == 0){
                            visit[col][row] = 0;
 //                           delete_have_neighbor_zero(visit);
                        }
                    }
                    if (row + 1 < 10) {
                        if (visit[col][row + 1] == 0){
                            visit[col][row] = 0;
 //                           delete_have_neighbor_zero(visit);
                        }
                    }
                    if (col - 1 >= 0) {
                        if (visit[col - 1][row] == 0){
                            visit[col][row] = 0;
 //                           delete_have_neighbor_zero(visit);
                        }
                    }
                    if (row - 1 >= 0) {
                        if (visit[col][row - 1] == 0){
                            visit[col][row] = 0;
  //                          delete_have_neighbor_zero(visit);
                        }
                    }
                }
            }

        }
    }


    public static void delete(int[][] grid, int[][] visit) {
        boolean delete_start=true;
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j <10 ; j++) {
                if (visit[i][j] == 9) {
                    if ((i < 9)) {
                        if (!((visit[i+1][j] == 2) || (visit[i+1][j] == 9))) delete_start = false;
                    }
                    if ((j < 9)) {
                        if (!((visit[i][j+1] == 2) || (visit[i][j+1] == 9))) delete_start = false;
                    }
                    if ((i >0)) {
                        if (!((visit[i-1][j] == 2) || (visit[i-1][j] == 9))) delete_start = false;
                    }
                    if ((j>0)) {
                        if (!((visit[i][j-1] == 2) || (visit[i][j-1] == 9))) delete_start = false;
                    }
                }
            }
        }
        if (delete_start){
            for (int i = 0; i <10 ; i++) {
                for (int j = 0; j <10 ; j++) {
                    if (visit[i][j]==9){

//                        if(grid[i][j]==1) count_delete_white++;
//                        if(grid[i][j]==2) count_delete_black++;
//                        System.out.println("delete "+ " col="+i+ " row="+j+ "color="+grid[i][j]);
//                        System.out.println("count "+count_delete_white+":"+count_delete_black );
                        grid[i][j]=0;
                    }


                }

            }
        }
    }

    private static void dfs(int col, int row, int color) {


        if (grid[col][row]==0) return;
        if (grid[col][row]!=color) { visit[col][row]=9; return;}
        visit[col][row]=1;

 //       System.out.println("in " + col+" "+row);
        if (col+1<10){if (visit[col+1][row]==0) dfs(col+1,row,color);}
        if ((col+1)<10&&(row+1)<10){ if (visit[col+1][row+1]==0) dfs(col+1,row+1,color); }
        if ((col+1)<10&&(row-1)>=0) {if (visit[col+1][row-1]==0) dfs(col+1,row-1,color);}
        if (row+1<10){ if (visit[col][row+1]==0) dfs(col,row+1,color);}
        if (row-1>=0){if (visit[col][row-1]==0) dfs(col,row-1,color); }
        if (col-1>=0){if (visit[col-1][row]==0) dfs(col-1,row,color); }
        if ((col-1)>=0&&(row+1)<5) {if (visit[col-1][row+1]==0) dfs(col-1,row+1,color);}
        if ((col-1)>=0&&(row-1)>=0){ if (visit[col-1][row-1]==0) dfs(col-1,row-1,color);}


        visit[col][row]=2;
 //       System.out.println("out " + col+" "+row);
        if ((col_start==col)&&(row_start==row)){
  //          System.out.println("Circle enable");
            circle=true;
        }
    }

    public static void input(int[][] grid) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <10 ; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    public static    int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

}
