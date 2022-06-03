package Game;

import java.awt.Point;
public class Labyrinth3 extends Game {
    /**
     * Constructeur qui définit le labyrinthe
     */
    public Labyrinth3(){
        super((short)25,(short)30,(byte)6, 2);

        grid[2][20] = 3;
        grid[7][7] = 3;
        grid[8][14] = 3;
        grid[9][21] = 3;
        grid[14][23] = 3;
        grid[21][12] = 3;
        ghostPosition[0] = new Point(2,20);
        ghostPosition[1]= new Point(7,7);
        ghostPosition[0] = new Point(8,14);
        ghostPosition[1]= new Point(9,21);
        ghostPosition[0] = new Point(14,23);
        ghostPosition[1]= new Point(21,12);

        grid[0][0] = 4;
        grid[0][1] = 4;
        grid[0][2] = 4;
        grid[0][3] = 4;
        grid[0][4] = 4;
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
        grid[0][18] = 4;
        grid[0][19] = 4;
        grid[0][20] = 4;
        grid[0][21] = 4;
        grid[0][22] = 4;
        grid[0][23] = 4;
        grid[0][24] = 4;
        grid[0][25] = 4;
        grid[0][26] = 4;
        grid[0][27] = 4;
        grid[0][28] = 4;
        grid[0][29] = 4;

        grid[1][0] = 4;
        grid[2][0] = 4;
        grid[3][0] = 4;
        grid[4][0] = 4;
        grid[5][0] = 4;
        grid[6][0] = 4;
        grid[7][0] = 4;
        grid[8][0] = 4;
        grid[9][0] = 4;
        grid[10][0] = 4;
        grid[11][0] = 4;
        grid[12][0] = 4;
        grid[13][0] = 4;
        grid[14][0] = 4;
        grid[15][0] = 4;
        grid[16][0] = 4;
        grid[17][0] = 4;
        grid[18][0] = 4;
        grid[18][0] = 4;
        grid[19][0] = 4;
        grid[20][0] = 4;
        grid[21][0] = 4;
        grid[22][0] = 4;
        grid[23][0] = 4;
        grid[24][0] = 4;
      
        grid[24][1] = 4;
        grid[24][2] = 4;
        grid[24][3] = 4;
        grid[24][4] = 4;
        grid[24][5] = 4;
        grid[24][6] = 4;
        grid[24][7] = 4;
        grid[24][8] = 4;
        grid[24][9] = 4;
        grid[24][10] = 4;
        grid[24][11] = 4;
        grid[24][12] = 4;
        grid[24][13] = 4;
        grid[24][14] = 4;
        grid[24][15] = 4;
        grid[24][16] = 4;
        grid[24][17] = 4;
        grid[24][18] = 4;
        grid[24][18] = 4;
        grid[24][19] = 4;
        grid[24][20] = 4;
        grid[24][21] = 4;
        grid[24][22] = 4;
        grid[24][23] = 4;
        grid[24][24] = 4;
        grid[24][25] = 4;
        grid[24][26] = 4;
        grid[24][27] = 4;
        grid[24][28] = 4;
        grid[24][29] = 4;

        grid[1][29] = 4;
        grid[2][29] = 4;
        grid[3][29] = 4;
        grid[4][29] = 4;
        grid[5][29] = 4;
        grid[6][29] = 4;
        grid[7][29] = 4;
        grid[8][29] = 4;
        grid[9][29] = 4;
        grid[10][29] = 4;
        grid[11][29] = 4;
        grid[12][29] = 4;
        grid[13][29] = 4;
        grid[14][29] = 4;
        grid[15][29] = 4;
        grid[16][29] = 4;
        grid[17][29] = 4;
        grid[18][29] = 4;
        grid[19][29] = 4;
        grid[20][29] = 4;
        grid[21][29] = 4;
        grid[22][29] = 4;
        grid[23][29] = 4;
        grid[24][29] = 4;
        
        grid[7][1] = 4;
        grid[7][2] = 4;
        grid[7][3] = 4;
        grid[7][4] = 4;
        grid[7][5] = 4;
        grid[7][6] = 4;
        grid[3][6] = 4;
        grid[4][6] = 4;
        grid[5][6] = 4;
        grid[6][6] = 4;
        grid[3][5] = 4;
        grid[3][4] = 4;
        grid[3][3] = 4;
        grid[4][3] = 4;
        grid[5][3] = 4;

        grid[1][10] = 4;
        grid[2][10] = 4;
        grid[3][10] = 4;
        grid[4][10] = 4;
        grid[5][10] = 4;
        grid[1][11] = 4;
        grid[2][11] = 4;
        grid[3][11] = 4;
        grid[4][11] = 4;
        grid[5][11] = 4;

        grid[1][18] = 4;
        grid[2][18] = 4;
        grid[3][18] = 4;
        grid[4][18] = 4;

        grid[7][23] = 4;
        grid[7][24] = 4;
        grid[7][25] = 4;
        grid[7][26] = 4;
        grid[7][27] = 4;
        grid[7][28] = 4;
        grid[6][23] = 4;
        grid[5][23] = 4;
        grid[4][23] = 4;
        grid[3][23] = 4;
        grid[3][24] = 4;
        grid[3][25] = 4;
        grid[3][26] = 4;
        grid[4][26] = 4;
        grid[5][26] = 4;

        grid[8][23] = 4;
        grid[8][24] = 4;
        grid[9][24] = 4;
        grid[10][24] = 4;
        grid[10][23] = 4;
        grid[10][22] = 4;

        grid[8][10] = 4;
        grid[8][11] = 4;
        grid[8][12] = 4;
        grid[9][12] = 4;
        grid[10][12] = 4;
        grid[10][13] = 4;
        grid[10][14] = 4;
        grid[10][15] = 4;

        grid[10][3] = 4;
        grid[10][4] = 4;
        grid[10][5] = 4;
        grid[10][6] = 4;
        grid[11][3] = 4;
        grid[12][3] = 4;
        grid[13][3] = 4;
        grid[14][1] = 4;
        grid[14][2] = 4;
        grid[14][3] = 4;
        grid[14][4] = 4;
        grid[14][5] = 4;
        grid[14][6] = 4;
        grid[15][6] = 4;
        grid[16][6] = 4;
        grid[17][6] = 4;
        grid[18][6] = 4;
        grid[18][5] = 4;
        grid[18][4] = 4;
        grid[18][3] = 4;
        grid[17][3] = 4;
        grid[16][3] = 4;
        
        grid[15][10] = 4;
        grid[16][10] = 4;
        grid[17][10] = 4;
        grid[18][10] = 4;
        grid[19][10] = 4;
        grid[20][10] = 4;
        grid[21][10] = 4;
        grid[22][10] = 4;
        grid[23][10] = 4;
        grid[15][11] = 4;
        grid[15][12] = 4;
        grid[15][13] = 4;
        grid[14][13] = 4;
        grid[13][13] = 4;
      
        grid[16][17] = 4;
        grid[17][17] = 4;
        grid[18][17] = 4;
        grid[19][17] = 4;
        grid[20][17] = 4;
        grid[21][17] = 4;
        grid[16][18] = 4;
        grid[17][18] = 4;
        grid[18][18] = 4;
        grid[19][18] = 4;
        grid[20][18] = 4;
        grid[21][18] = 4;
      

        grid[12][19] = 4;
        grid[13][19] = 4;
        grid[13][20] = 4;
        grid[13][21] = 4;
        grid[14][21] = 4;
        grid[15][21] = 4;
        grid[16][21] = 4;
        grid[16][22] = 4;
        grid[15][23] = 4;
        grid[15][24] = 4;
        grid[15][25] = 4;
        grid[15][26] = 4;
        grid[15][27] = 4;
        grid[15][28] = 4;
        grid[16][23] = 4;
        grid[17][23] = 4;
        grid[18][23] = 4;
        grid[19][23] = 4;
        grid[19][24] = 4;
        grid[19][25] = 4;
        grid[19][26] = 4;
        grid[18][26] = 4;
        grid[17][26] = 4;
    

        for(int i = 0; i< this.getHeight();i++){
            for(int j = 0; j< this.getWidth();j++){
                if(grid[i][j] != 4 && grid[i][j] != 3){
                   grid[i][j] = 1;

                }
            }
        }
    }
   
}
