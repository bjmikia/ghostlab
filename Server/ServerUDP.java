package Server;
import Game.*;
import Utils.*;
import java.awt.Point;
import java.net.*;

public class ServerUDP {

	
    /**
     * Retourne le joueur gagnant dans une partie (selon score)
     * @param game la partie
     * @param dso DatagramSocket
     * @param ia InetSocketAddress
     * @return  le joueur gagnant
     */

    public static Player getWinner (Game game, DatagramSocket dso, InetSocketAddress ia ){
        int max_score = 0 ;
        int score = 0;
        Player player= null;
        for(Player p : game.getPlayers()){
            score = p.getPoint();
            if (score > max_score ){
                max_score = score;
                player = p;
            }
        }
        return player;
    }
    
    /**
     * Fin de partie, envoie du gagnant et de ses points
     * @param game la partie
     * @param dso DatagramSocket
     * @param ia InetSocketAddress
     * @throws Exception
     */

	public static void endGame (Game game, DatagramSocket dso, InetSocketAddress ia ) throws Exception{
	    byte[]data;
	    Player winner = getWinner(game,dso,ia);
	    // à changer dès que BJM mets à jour sa classe Player
	    String id  = winner.getName();
	    String p = winner.getPointstr();
	    String s = "ENDGA " + id +  p + "+++";
	    data = s.getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length,ia);
        dso.send(packet);
        dso.close(); 

	}

    /**
     * Envoie de la nouvelle position des fantomes 
     * @param positionFant les coordonnées de chaque fantome
     * @param dso DatagramSocket
     * @param ia InetSocketAddress
     * @throws Exception
     */

	public static void ghostMoved (Point[] positionFant , DatagramSocket dso, InetSocketAddress ia  ) throws Exception{
        byte[]data;
        for(Point p: positionFant){
            String x = Utils.goodFormat(3,(int)p.getX(),"0");
            String y = Utils.goodFormat(3,(int)p.getY(),"0");
            String s = "GHOST " + x +" " + y  + "+++";
            data = s.getBytes();
            DatagramPacket packet = new DatagramPacket(data,data.length,ia);
            dso.send(packet);
        }

	}

    /**
     * Envoie du nouveau score du joueur qui a attrapé un fantome
     * @param player le joueur
     * @param dso DatagramSocket
     * @param ia InetSocketAddress
     * @throws Exception
     */
	public static void ghostTakenByPlayer (Player player , DatagramSocket dso, InetSocketAddress ia  ) throws Exception{
    	byte[]data;
    	String id = player.getName();
        String p = player.getPointstr();
        String x = player.getX();
        String y = player.getY();
    	String s = "SCORE " + id+ " "  + p + " " + x + " " + y + "+++";
        data = s.getBytes();
    
        DatagramPacket packet = new DatagramPacket(data,data.length,ia);
        dso.send(packet);
    }

    /**
     * 
     *  Envoie en udp le message de la part du Joueur p sur le port du joueur pSend
     * @param p le joueur qui envoie le message 
     * @param mess le message à envoyer
     * @param pSend le joueur qui reçoit le message
     * @throws Exception
     */

    public static void sendMessagePlayerToOtherPlayer (Player p , String mess , Player pSend ) throws Exception{
        DatagramSocket dso = new DatagramSocket();
    	byte[]data;
    	String s = "MESSP " + p.getName()+ " " + mess + "+++";
        data = s.getBytes();
        InetSocketAddress dest = new InetSocketAddress(pSend.getIpPlayer(), Integer.parseInt(pSend.getPort()));
        DatagramPacket packet = new DatagramPacket(data,data.length,dest);
        dso.send(packet);

    }

   /**
     *  Envoie le message en udp à tous les joueurs 
     * @param dso DatagramSocket
     * @param ia InetSocketAddress
     * @param mess le message
     * @param id l'expéditeur
     * @throws Exception
     */

    public static void sendMessagePlayerToAllPlayers ( DatagramSocket dso, InetSocketAddress ia ,String mess , String id) throws Exception{
        byte[]data;
        String s = "MESSA " + id + " " + mess + "+++";
        data = s.getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length,ia);
        dso.send(packet);
    }

}
