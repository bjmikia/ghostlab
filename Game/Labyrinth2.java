package Game;
import java.awt.Point;

public class Labyrinth2 extends Game {

    /**
     * Constructeur qui définit le labyrinthe
     */

    public Labyrinth2(){
        
        super((short)10,(short)15,(byte)2, 2);

        grid[1][13] = 3;
        grid[5][0] = 3;
        ghostPosition[0] = new Point(1,13);
        ghostPosition[1]= new Point(5,0);


        grid[0][3] = 4;
        grid[0][4] = 4;
        grid[0][7] = 4;
        grid[0][11] = 4;
        grid[0][12] = 4;
        grid[0][13] = 4;
        grid[0][14] = 4;
        grid[1][3] = 4;
        grid[1][4] = 4;
        grid[1][7] = 4;
        grid[1][11] = 4;
        grid[1][14] = 4;
        grid[2][7] = 4;
        grid[2][11] = 4;
        grid[2][14] = 4;
        grid[3][7] = 4;
        grid[3][11] = 4;
        grid[3][12] = 4;
        grid[3][2] = 4;


        grid[4][0] = 4;
        grid[4][1] = 4;
        grid[4][2] = 4;
        grid[4][14] = 4;
        grid[5][6] = 4;
        grid[5][10] = 4;
        grid[5][11] = 4;
        grid[5][12] = 4;
        grid[5][13] = 4;
        grid[5][14] = 4;
        grid[6][0] = 4;
        grid[6][6] = 4;
        grid[6][10] = 4;

        grid[7][0] = 4;
        grid[7][1] = 4;
        grid[7][6] = 4;
        grid[7][7] = 4;
        grid[7][8] = 4;
        grid[7][10] = 4;
        grid[7][11] = 4;
        grid[8][0] = 4;
        grid[8][1] = 4;
        grid[8][8] = 4;
        grid[9][0] = 4;
        grid[9][1] = 4;
        grid[9][2] = 4;
        grid[9][3] = 4;
        grid[9][4] = 4;
        grid[9][5] = 4;
        grid[9][8] = 4;
        grid[9][9] = 4;
        grid[9][10] = 4;
        grid[9][11] = 4;
        grid[9][12] = 4;
        grid[9][13] = 4;
        grid[9][14] = 4;
      
        for(int i = 0; i< this.getHeight();i++){
            for(int j = 0; j< this.getWidth();j++){
                if(grid[i][j] != 4 && grid[i][j] != 3){
                   grid[i][j] = 1;

                }
            }
        }

    }

}