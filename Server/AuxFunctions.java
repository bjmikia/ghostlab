package Server;
import Game.*;
import Utils.*;
import java.awt.Point;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
public class AuxFunctions {
    List<Game> games;
    public AuxFunctions(List<Game> p){
        games = p;
    }


    /**
     * Création d'un nouveau joueur  qui rejoint une partie déjà existante
     * @param pw le flux de sortie
     * @param id le pseudo du joueur
     * @param port le port du joueur
     * @param m le numéro de la partie
     * @param socket la socket de communication
     * @return j le nouveau joueur
     */

    public Player JoinOne(PrintWriter pw, String id, String port, int m, Socket socket ){
        port = port.replace("*", "");
        port = port.trim();
        Player j = new Player(Utils.correctName(id), new Point(0, 0), port, socket.getInetAddress().getHostAddress());
        byte gameNumber =(byte) m;
        synchronized(games){
            for (Game g : games) {
                if (gameNumber== g.getId()) {
                    if(!Utils.isCorrectPortAndName(port,id,g)){
                        pw.print("REGNO***");
                        pw.flush();
                        return null;
                    }
                    if(g.addPlayer(j)){
                        j.setLabyrinth(g);
                        pw.print("REGOK " + g.getId() + "***");
                        pw.flush();
                        return j;
                    }
                }
            }
        }
        pw.print("REGNO***");
        pw.flush();
        return null;
    }


    /**
     * Création d'un nouveau joueur + un novueau jeu, le labyrinthe étant choisi aléatoirement
     * @param pw le flux de sortie
     * @param id le pseudo du joueur 
     * @param port le port du joueur 
     * @param socket la socket de communucation
     * @return j le nouveau joueur
     */

    public Player newGame(PrintWriter pw, String id, String port, Socket socket ) {
        if(!Utils.isCorrectPort(port)){
            pw.print("REGNO***");
            pw.flush();
            return null;
        }
        Player j = new Player(Utils.correctName(id), new Point(0, 0),port,socket.getInetAddress().getHostAddress());
        Game game;
        int n = Utils.ourRandom(3);
        switch (n) {
            case 1:
                game = new Labyrinth1();
                game.newUDPsock();
                j.setLabyrinth(game);
               
                game.addPlayer(j);
                synchronized(games){
                    games.add(game);
                }
                pw.print("REGOK "+ game.getId()+ "***");
                pw.flush();
                return j;


            case 2:
                game = new Labyrinth2();
                game.newUDPsock();
                j.setLabyrinth(game);
                
                game.addPlayer(j);
                synchronized(games){
                    games.add(game);
                }

                pw.print("REGOK "+ game.getId()+ "***");
                pw.flush();
                return j;

            case 3:
                game = new Labyrinth3();
                game.newUDPsock();
                j.setLabyrinth(game);
               
                game.addPlayer(j);
                synchronized(games){
                    games.add(game);
                }

                pw.print("REGOK "+ game.getId()+ "***");
                pw.flush();
                return j;

            default:
                pw.print("REGNO***");
                pw.flush();
                return null;
        }
    }

  
    /**
     * Demander la taille d'un labyrinthe sur une partie
     * @param pw le flux de sortie 
     * @param idPartie le numéro de la partie
     */

    public void askSize(PrintWriter pw, int idPartie) {
        byte id = (byte) idPartie;
        synchronized(games){
            for (Game g : games) {
                if (id == g.getId()) {
                    short h = g.getHeight();
                    short w = g.getWidth();
                    pw.print("SIZE! " + id + " " + Short.reverseBytes(h) + " " +Short.reverseBytes(w)+ "***");
                    pw.flush();
                    return;
                }
            }
        }
        pw.print("DUNNO***");
        pw.flush();
    }

    /**
     * Demander le nombre de joueur sur une partie
     * @param pw le flux de sortie 
     * @param idPartie le numéro de la partie 
     */

    public void askPlayer(PrintWriter pw, int idPartie) {
        byte id = (byte) idPartie;
        synchronized(games){
            for (Game g : games) {
                if (id == g.getId()) {
                   
                    byte nbPlayers = (byte) g.getPlayers().size();
                    pw.print("LIST! " + id + " " + nbPlayers  + "***");
                    pw.flush();
                    for (Player joueur : g.getPlayers()) {
                        pw.print("PLAYR " + joueur.getName());
                        pw.flush();
                    }
                    return;
                }
            }
        }
        pw.print("DUNNO***");
        pw.flush();
    }


    /**
     * Demander le nombre de partie qui n'ont pas encore commencés
     * @param pw le flux de sortie
     */

    public void askGames(PrintWriter pw) {
        int cmpt = 0;
        List<Game> noStart = new ArrayList<>();
        synchronized(games){
            for (Game g : games) {
                if (!g.isPlayersAllStart()) {
                    noStart.add(g);
                    cmpt++;
                }
            }

            pw.print("GAMES " + (byte)cmpt + "***");

            pw.flush();

            for (Game g : noStart) {
                
                byte nbPlayers = (byte) g.getPlayers().size();
                for(int i=0 ; i< nbPlayers ; i++){
                    
                    pw.print("OGAME " + g.getId() + " " + nbPlayers + "***");
                    pw.flush();
                }

            }
        }
    }

    
    /**
     * Lorsque la partie commence, envoie des informations sur le labyrinthe + sur l'envoie UDP
     * @param pw le flux de sortie
     * @param j le joueur
     */

    public void welcome(PrintWriter pw, Player j ) {
        Game game = j.getLabyrinth();
        byte m = (byte)game.getId();
        short h = game.getHeight();
        short w = game.getWidth();
        byte f = game.getGhosts();
        String ip =game.ia.getAddress().getHostAddress();
        String port = Integer.toString(game.ia.getPort());

        pw.print("WELCO " + m + " " + Short.reverseBytes(h) + " " + Short.reverseBytes(w) + " " + f + " " + ip + " " + port + "***");
        pw.flush();
    }


    /**
     * Envoyer au joueur sa position de départ
     * @param pw le flux de sortie 
     * @param j le joueur
     */
    public void posit(PrintWriter pw, Player j){
        String name = j.getName();
        String x = j.getX();
        String y = j.getY();
        pw.print("POSIT"+ " " + name + " " + x + " " + y + "***");
        pw.flush();
    }

    /**
     * Savoir si un joueur est dans la partie ou pas 
     * pour l'envoi de message privé
     * @param name le nom du joueur recherché
     * @param j le joueur qui souhaite envoyer le message
     * @return boolean
     */
    public boolean isInPartie(String name, Player j){
        Game game = j.getLabyrinth();
        for(Player joueur: game.getPlayers()){
            if (joueur.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * Envoyer au joueur sa position nouvelle position et son nombre de points si il a attrapé un fantome
     * @param pw le flux de sortie
     * @param j le joueur
     */
    public void MessMove(PrintWriter pw, Player j){
        String x = j.getX();
        String y = j.getY();
        j.getLabyrinth().displayLabyrinth();
        System.out.println("\n\n\n");
        if(j.getGhostCaught()){
            j.setGhostCaught(false);
            String p = j.getPointstr();
            pw.print("MOVEF!" + " " + x +" "+ y + " "+ p + "***");
            pw.flush();
            
            try {
                ServerUDP.ghostTakenByPlayer(j,j.getLabyrinth().getDataSock(), j.getLabyrinth().getInSockAdress());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            pw.print("MOVE!" + " " + x +" "+ y + "***");
            pw.flush();
            
        }
    }

    /**
     * Supprimer la partie
     * @param game Game
     */

    public void deletePartie(Game game){
        synchronized(games){
            games.removeIf(g ->g.getId() == game.getId());

        }
    }

    /**
     * Recuperer un joueur quand on a seulement son nom
     * @param name le nom du joueur
     * @param j pour avoir la partie courante
     * @return le joueur recherché
     */

    public Player getPlayerByName(String name, Player j){
        Game game = j.getLabyrinth();
        for (Player joueur : game.getPlayers()) {
            if (joueur.getName().equals(name)) {
                return joueur;
            }
        }
        return null;
    }
    

    
}
