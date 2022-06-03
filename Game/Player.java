package Game;
import Utils.*;
import java.awt.Point;


public class Player {
    private  String name;
    private Point position;
    private  String port;
    private String ip;
    private Game labyrinth;
    private int point;
    private  boolean isStart;
    private int idPlayer;
    private boolean ghostCaught;


    public Player(String name, Point p, String port, String ip){
        this.name = name;
        this.position = p;
        this.port = port;
        this.ip = ip;
        this.isStart = false;
        this.idPlayer = 0;
        this.ghostCaught = false;

    }

    /********************\
     * GETTER ET SETTER *
     /********************/

    public String getName() {
        return name; 
    }
    public  void setPosition(Point p){ 
        this.position = p;
    }
    public  Point getPosition(){ 
        return this.position;
    }
    public String getX(){
        return Utils.goodFormat(3, this.position.x,"0");
    }
    public String getY(){
        return Utils.goodFormat(3, this.position.y,"0");
    }
    public String getPort(){ 
        return this.port; 
    }

    public String getIpPlayer(){
        return ip;
    }
    public Game getLabyrinth(){
        return this.labyrinth;
    }
    public void setLabyrinth(Game l){
        this.labyrinth = l;
    }
    public void setPoint(int p){
        this.point += p;
    }
    public int getPoint(){ 
        return this.point;
    }
    public String getPointstr(){
        return Utils.goodFormat(4, this.point,"0");
    }
    public boolean getIsStart(){
        return this.isStart;
    }
    public void setIsStart( boolean st){
        this.isStart = st;
    }
    public int getIdPlayer(){
        return this.idPlayer; 
    }
    public void setIdPlayer(int id){
        this.idPlayer = id ;
    }
    
    public boolean getGhostCaught(){
         return this.ghostCaught;
        }
    public void setGhostCaught(boolean gc){
        this.ghostCaught = gc;
    }
    
    
}
