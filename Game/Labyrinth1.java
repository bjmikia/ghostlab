package Game;
import java.awt.Point;

public class Labyrinth1 extends Game {

    /**
     * Constructeur qui définit le labyrinthe
     */

    public Labyrinth1(){
        super((short)15,(short)20,(byte)5, 2);
        grid[1][2] = 3;
        grid[2][16] = 3;
        grid[7][2] = 3;
        grid[8][16] = 3;
        grid[13][6] = 3;
        ghostPosition[0] = new Point(1,2);
        ghostPosition[1]= new Point(2,16);
        ghostPosition[2]= new Point(7,2);
        ghostPosition[3]= new Point(8,16);
        ghostPosition[4]= new Point(13,6);
    

        grid[0][0] = 4;
        grid[0][1] = 4;
        grid[0][2] = 4;
        grid[0][3] = 4;
        grid[0][5] = 4;
        grid[0][6] = 4;
        grid[0][7] = 4;
        grid[0][8] = 4;
        grid[0][9] = 4;
        grid[0][10] = 4;
        grid[0][11] = 4;
        grid[0][12] = 4;
        grid[0][13] = 4;
        grid[0][14] = 4;
        grid[0][15] = 4;
        grid[0][16] = 4;
        grid[0][17] = 4;
        grid[0][18] = 4;
        grid[0][19] = 4;

        grid[1][0] = 4;
        grid[1][1] = 4;
        grid[1][5] = 4;
        grid[1][6] = 4;
        grid[1][13] = 4;
        grid[1][17] = 4;
        grid[1][18] = 4;
        grid[1][19] = 4;
        grid[1][0] = 4;
        grid[1][1] = 4;
        grid[1][5] = 4;
        grid[1][6] = 4;
        grid[1][13] = 4;
        grid[1][17] = 4;
        grid[1][18] = 4;
        grid[1][19] = 4;

        grid[2][0] = 4;
        grid[2][5] = 4;
        grid[2][6] = 4;
        grid[2][13] = 4;
        grid[2][18] = 4;
        grid[2][19] = 4;

        grid[3][0] = 4;
        grid[3][1] = 4;
        grid[3][13] = 4;
        grid[3][18] = 4;
        grid[3][19] = 4;

        grid[4][0] = 4;
        grid[4][1] = 4;
        grid[4][11] = 4;
        grid[4][12] = 4;
        grid[4][13] = 4;
        grid[4][18] = 4;
        grid[4][19] = 4;

        grid[5][0] = 4;
        grid[5][1] = 4;
        grid[5][2] = 4;
        grid[5][3] = 4;
        grid[5][13] = 4;
    

        grid[6][0] = 4;
        grid[6][7] = 4;
        grid[6][8] = 4;
        grid[6][9] = 4;
        grid[6][10] = 4;
        grid[6][11] = 4;
        grid[6][16] = 4;
        grid[6][17] = 4;
        grid[6][18] = 4;
        grid[6][19] = 4;

        grid[7][0] = 4;
        grid[7][7] = 4;
        grid[7][18] = 4;
        grid[7][19] = 4;

        grid[8][0] = 4;
        grid[8][1] = 4;
        grid[8][2] = 4;
        grid[8][3] = 4;
        grid[8][4] = 4;
        grid[8][5] = 4;
        grid[8][6] = 4;
        grid[8][7] = 4;
        grid[8][8] = 4;
        grid[8][18] = 4;
        grid[8][19] = 4;

        grid[9][0] = 4;
        grid[9][8] = 4;
        grid[9][5] = 4;
        grid[9][6] = 4;
        grid[9][13] = 4;
        grid[9][14] = 4;
        grid[9][15] = 4;
        grid[9][16] = 4;
        grid[9][17] = 4;
        grid[9][18] = 4;
        grid[9][19] = 4;

        grid[10][0] = 4;
        grid[10][4] = 4;
        grid[10][5] = 4;
        grid[10][6] = 4;
        grid[10][7] = 4;
        grid[10][8] = 4;
        grid[10][13] = 4;
        grid[10][14] = 4;
        grid[10][15] = 4;
        grid[10][16] = 4;
        grid[10][17] = 4;
        grid[10][18] = 4;
        grid[10][19] = 4;

        grid[11][0] = 4;
        grid[11][5] = 4;
        grid[11][6] = 4;
        grid[11][13] = 4;
        grid[11][14] = 4;
        grid[11][15] = 4;
        grid[11][16] = 4;
        grid[11][17] = 4;
        grid[11][18] = 4;
        grid[11][19] = 4;

        grid[12][0] = 4;
        grid[12][1] = 4;
        grid[12][2] = 4;
        grid[12][10] = 4;
        grid[12][13] = 4;
        grid[12][14] = 4;
        grid[12][15] = 4;
        grid[12][16] = 4;
        grid[12][17] = 4;
        grid[12][18] = 4;
        grid[12][19] = 4;

        grid[13][0] = 4;
        grid[13][1] = 4;
        grid[13][10] = 4;
        grid[13][13] = 4;
        grid[13][14] = 4;
        grid[13][15] = 4;
        grid[13][16] = 4;
        grid[13][17] = 4;
        grid[13][18] = 4;
        grid[13][19] = 4;

        grid[14][0] = 4;
        grid[14][1] = 4;
        grid[14][2] = 4;
        grid[14][3] = 4;
        grid[14][4] = 4;
        grid[14][5] = 4;
        grid[14][6] = 4;
        grid[14][7] = 4;
        grid[14][8] = 4;
        grid[14][9] = 4;
        grid[14][10] = 4;
        grid[14][13] = 4;
        grid[14][14] = 4;
        grid[14][15] = 4;
        grid[14][16] = 4;
        grid[14][17] = 4;
        grid[14][18] = 4;
        grid[14][19] = 4;

        for(int i = 0; i< this.getHeight();i++){
            for(int j = 0; j< this.getWidth();j++){
                if(grid[i][j] != 4 && grid[i][j] != 3){
                    grid[i][j] = 1;

                }
            }
        }
    }
    
}
