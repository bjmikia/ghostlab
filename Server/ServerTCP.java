package Server;
import Game.*;
import Utils.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class ServerTCP implements Runnable {
    Socket socket;
    List<Game> games;
    Player j;

    AuxFunctions auxFunctions;

    public ServerTCP(Socket sock, List<Game> parties) {
        this.games = parties;
        this.auxFunctions = new AuxFunctions(parties);
        socket = sock;
        this.j = null;
    }

    /**
     * Pour se désinscrire d'une partie
     * @param pw le flux de sortie
     *
     */
    public void unregister(PrintWriter pw) {
        synchronized(games){
            for (Game g : games) {
                if (j.getLabyrinth().getId() == g.getId()) {
                    byte tmp =  j.getLabyrinth().getId();
                    j.getLabyrinth().getPlayers().remove(j);
                    j=null;
                    pw.print("UNROK " + tmp + "***");
                    pw.flush();
                    return;
                }
            }
        }
        pw.print("DUNNO***");
        pw.flush();
    }

    /**
     * Gestion de la commande START, qui entraine le blocage du terminal côté client
     * @param pw le flux de sortie
     */

    public void start(PrintWriter pw) {
        if(j == null){
            System.out.println("Client not registered");
            return;
        }

        if (j.getLabyrinth()!= null  && j.getLabyrinth().getId() != 0) {
            j.setIsStart(true);
            System.out.println("Client start");
        } else {
            System.out.println("Client not registered");
            pw.print("DUNNO***");
            pw.flush();
        }
    }
    /**
     * Initialise la partie game en plaçant les joueurs et en lançant le
     * déplacement des fantômes
     * @param game
     */
    public void initialisation( Game game){
        synchronized(game){
            game.setPlayersIds();
            game.positionStart();
            if(!game.getFirstLaunch()){
                game.displayLabyrinth();
                System.out.println("\n\n\n");
                game.launchGhostMove();
                game.setFirstLaunch(true);
            }
        }
    }


    /**
     * Switch qui gère les fonctions a envoyer en lisant ce que le joueur souhaite faire avant le commencement d'une partie
     * @param br le flux d'entrée
     * @param pw le flux de sortie
     * @throws Exception
     */

    public void choiceBeforeGame (InputStream br, PrintWriter pw) throws IOException{
        byte[]cbuf= new byte[5];
        if(br.available()!=0){
            br.read(cbuf,0,5) ;
        }

        String  mess = new String(cbuf,0,5);
        switch (mess) {
            case "NEWPL" :
                if(j != null && j.getLabyrinth() != null){
                    pw.print("REGNO***");
                    pw.flush();
                    break;
                }
                byte[]buf= new byte[21];
                br.read(buf,0,21) ;
                String  mess1 = new String(buf,0,21);
                mess1 = mess1.replace('*', ' ');
                String[] words = mess1.split(" ");
                words[2] = words[2].trim();
                j = auxFunctions.newGame(pw, words[1], words[2], socket);
                break;

            case  "REGIS" :
                if(j != null && j.getLabyrinth() != null){
                    pw.print("REGNO***");
                    pw.flush();
                    break;
                }
                byte[]buf2= new byte[19];
                br.read(buf2,0,19) ;
                String  mess2 = new String(buf2,0,19);
                int  m2 = buf2[15]& 0xFF;
                String[] words2 = mess2.split(" ");
                j = auxFunctions.JoinOne(pw, words2[1], words2[2], m2 , socket);
                break;

            case "UNREG":
                byte[]buf21= new byte[3];
                br.read(buf21,0,3) ;
                unregister(pw);

                break;

            case "SIZE?":
                byte[]buf3= new byte[5];
                br.read(buf3,0,5) ;
                int m3 = buf3[1]& 0xFF;
                auxFunctions.askSize(pw, m3);

                break;

            case "LIST?":
                byte[]buf4= new byte[5];
                br.read(buf4,0,5) ;
                int m4 = buf4[1]& 0xFF;
                auxFunctions.askPlayer(pw,m4);

                break;

            case "GAME?":

                byte[]buf22= new byte[3];
                br.read(buf22,0,3) ;
                auxFunctions.askGames(pw);

                break;

            case "NEWPT":
            if(j != null && j.getLabyrinth() != null){
                pw.print("REGNO***");
                pw.flush();
                break;
            }
            byte[]buf24= new byte[21];
            br.read(buf24,0,21) ;
            String  mess24 = new String(buf24,0,21);
            mess1 = mess24.replace('*', ' ');
            String[] words24 = mess24.split(" ");
            words24[2] = words24[2].trim();
            j = auxFunctions.newGame(pw, words24[1], words24[2], socket);

                break;
            case "START":
                byte[]buf23= new byte[3];
                br.read(buf23,0,3) ;
                start(pw);
                break;
            default :
                pw.print("DUNNO***");
                pw.flush();
                break;


        }
    }


    /**
     * Switch qui gère les fonctions a envoyer en lisant ce que le joueur souhaite faire durant une partie
     * @param br le flux d'entrée
     * @param pw le flux de sortie
     * @throws Exception
     */

    public void choiceDuringGame (InputStream br, PrintWriter pw) throws Exception{
        
        byte[]cbuf= new byte[5];
        if(br.available()!=0){
            br.read(cbuf,0,5) ;
        }
        String  mess = new String(cbuf,0,5);
        if(!( j.getLabyrinth().endGame())) {
            switch (mess) {

                case "UPMOV":
                    byte[]buf1= new byte[10];
                    br.read(buf1,0,10);
                    String  mess1 = new String(buf1, StandardCharsets.UTF_8);
                    String[] splitted = mess1.split(" ");
                    String number =splitted[1].replace("*","");
                    
                    int d1 = Utils.goodMove(number);
                    j.getLabyrinth().upMove(j, d1);
                    auxFunctions.MessMove(pw,j);
                    break;

                case "DOMOV":

                    byte[]buf2= new byte[10];
                    br.read(buf2,0,10) ;
                    String  mess2 = new String(buf2, StandardCharsets.UTF_8);
                    String[] splitted2 = mess2.split(" ");
                    String number2 = splitted2[1].replace("*","");
                    int d2 = Utils.goodMove(number2);
                    j.getLabyrinth().doMove(j, d2);
                    auxFunctions.MessMove(pw,j);
                    break;

                case "LEMOV":

                    byte[]buf3= new byte[10];
                    br.read(buf3,0,10) ;
                    String  mess3 = new String(buf3, StandardCharsets.UTF_8);
                    String[] splitted3 = mess3.split(" ");
                    String number3 =splitted3[1].replace("*","");
                    int d3 = Utils.goodMove(number3);
                    j.getLabyrinth().leMove(j, d3);
                    auxFunctions.MessMove(pw,j);

                    break;
                case "RIMOV":

                    byte[]buf4= new byte[10];
                    br.read(buf4,0,10) ;
                    String  mess4 = new String(buf4, StandardCharsets.UTF_8);
                    String[] splitted4 = mess4.split(" ");
                    String number4 = splitted4[1].replace("*","");
                    int d4 = Utils.goodMove(number4);
                    j.getLabyrinth().riMove(j, d4);
                    auxFunctions.MessMove(pw,j);

                    break;

                case "IQUIT":
                    byte[]buf5= new byte[3];
                    br.read(buf5,0,3) ;

                    pw.print("GOBYE***");
                    pw.flush();
                    pw.close();
                    br.close();
                    socket.close();
                    break;
                case "GLIS?":

                    byte nbPlayers =(byte)j.getLabyrinth().getPlayers().size();
                    pw.print("GLIS! " + nbPlayers + "***");
                
                    for (Player joueur : j.getLabyrinth().getPlayers()) {
                        pw.print("GPLYR " + joueur.getName() + " " + joueur.getX() + " " + joueur.getY() + " " + joueur.getPointstr() + "***");
                        pw.flush();
                    }
                    pw.flush();

                    break;
                case "MALL?":

                    String message2 = Utils.takeMessage(br);
                    ServerUDP.sendMessagePlayerToAllPlayers(j.getLabyrinth().getDataSock(), j.getLabyrinth().getInSockAdress(),message2,j.getName());
                    pw.print("MALL!***");
                    pw.flush();
                    break;

                case "SEND?":
                    byte[]buf6 = new byte[10];
                    br.read(buf6,0,10);
                   
                    String nameplayerSend = new String(buf6,1,8);
                    String message3 = Utils.takeMessage(br);
                    

                    if (!auxFunctions.isInPartie(nameplayerSend, j) ) {
                        pw.print("NSEND***");
                        pw.flush();
                    } else { 
                        ServerUDP.sendMessagePlayerToOtherPlayer(j,message3,auxFunctions.getPlayerByName(nameplayerSend, j));
                        pw.print("SEND!***");
                        pw.flush();
                    }
                    break;
            }
        }else{
            pw.print("GOBYE***");
            pw.flush();
            pw.close();
            br.close();
            socket.close();
        }
    }


    
     /**
     * Fonction qui gère la communication TCP entre le serveur et un joueur. Appel des fonctions.
     */

    @Override
    public void run(){
        try {
            InputStream br=socket.getInputStream();
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            auxFunctions.askGames(pw);
            do{
                if(br.available()!=0){
                    choiceBeforeGame(br, pw);
                }

            }while(j==null ||!j.getIsStart());

            Game labyrinthe = j.getLabyrinth();
            while(!labyrinthe.isPlayersAllStart()){Thread.sleep(100);}
            auxFunctions.welcome(pw, j);
            initialisation(labyrinthe);
            auxFunctions.posit(pw, j);

            while(!labyrinthe.endGame()){
                choiceDuringGame(br,pw);
            }
            if(labyrinthe.endGame()){
                ServerUDP.endGame(labyrinthe,labyrinthe.getDataSock(), labyrinthe.getInSockAdress()); 
                Utils.setAddrIp(labyrinthe.ia);
                auxFunctions.deletePartie(labyrinthe);
                br.close();
                pw.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("deconnexion ...");
            if(j != null && j.getLabyrinth() != null){
                int num = j.getLabyrinth().getId();
                if( num != 0) {
                    if(j.getLabyrinth().getPlayers().size()==1){
                        synchronized(games){
                            games.remove(j.getLabyrinth());
                        }
                    }
                    j.getLabyrinth().getPlayers().removeIf(player -> player.getName().equals(j.getName()));
                }
            }

        }
    }
}
