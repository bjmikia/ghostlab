package Game;
import Server.ServerUDP;
import Utils.*;
import java.awt.Point;
import java.net.*;
import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;

/**
 * Classe qui contient le squelette du jeu
 */

public class Game {

    int[][] grid;
    Point[] ghostPosition;
    private static byte idacc = 1;
    private byte id;
    private int nbPlayers;
    private short height;
    private short width;
    private boolean firstLaunch;
    private byte ghosts;
    private byte compteurGhosts = 0;
    public ArrayList<Player> players;
    public InetSocketAddress ia;
    public DatagramSocket dso;


    public Game(short h, short l, byte ghosts, int nbPlayers) {
        this.height = h;
        this.width = l;
        this.ghosts = ghosts;
        this.grid = new int[height][width];
        this.ghostPosition = new Point[this.ghosts];
        players = new ArrayList<>();
        this.nbPlayers = nbPlayers;
        this.firstLaunch = false;
        this.id = idacc++;


    }

     /********************\
     * GETTER ET SETTER *
    /********************/

    public byte getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public byte getGhosts() {
        return ghosts;
    }


    public byte getCpmtGhosts() {
        return compteurGhosts;
    }

    public short getHeight() {
        return height;
    }


    public short getWidth() {
        return width;
    }

    public boolean getFirstLaunch() {
        return firstLaunch;
    }

    public void setFirstLaunch(boolean b) {
        firstLaunch = b;
    }

    public DatagramSocket getDataSock() {
        return this.dso;
    }

    public void setDataSock(DatagramSocket d) {
        this.dso = d;
    }

    public InetSocketAddress getInSockAdress() {
        return this.ia;
    }

    public void setInSockAdress(InetSocketAddress i) {
        this.ia = i;
    }

    /**
     * Ajoute un joueur dans une partie
     * @param j  : le joueur
     * @return boolean
     */
    public boolean addPlayer(Player j) {
        if (!this.isPlayersAllStart() && this.players.size() < nbPlayers) {
            this.players.add(j);
            return true;
        }
        return false;

    }

    /**
     * Vérifie que la taille de la liste de players est egale 
     * au nombre de joueur pour cette partie et egalement si tous les players
     * ont envoyé start
     * @return boolean
     */
    public boolean isPlayersAllStart() {
        if (this.players.size() != nbPlayers) {
            return false;
        }
        for (Player j : players) {
            if (!j.getIsStart()) {
                return false;
            }
        }
        return true;
    }
    
     /**
     * Affichage du labyrinthe
     */

    public void displayLabyrinth() {
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (ints[j] == 1) {
                    System.out.print(" . ");
                } else if (ints[j] == 2) {
                    System.out.print(" o ");
                } else if (ints[j] == 3) {
                    System.out.print(" @ ");
                } else if (ints[j] == 4) {
                    System.out.print(" X ");
                } else if (ints[j] == 5) {
                    System.out.print(" # ");
                } else if (ints[j] == 6) {
                    System.out.print(" % ");
                } else if (ints[j] == 7) {
                    System.out.print(" $ ");
                } else if (ints[j] == 8) {
                    System.out.print(" £ ");
                } else if (ints[j] == 9) {
                    System.out.print(" G ");
                } else {
                    System.out.print(" ? ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Donne a chaque joueur un id pour l'affichage dans le terminal
     */
    public void setPlayersIds() {
        int id = 5;
        for (Player j : players) {
            if (j.getIdPlayer() != 0) {
                return;
            }
            j.setIdPlayer(id);
            id++;
        }
    }

    /**
     * Les fonctions de déplacement HAUT BAS GAUCHE DROITE
     * @param j le joueur
     * @param d la distance
     */

    public void upMove(Player j, int d) {
        if (d == 0) return;
        int x = j.getPosition().x;
        int y = j.getPosition().y;
        if (x == 0 || grid[x - 1][y] == 4) return;
        if (grid[x - 1][y] == 3) {
            j.setGhostCaught(true);
            compteurGhosts++;
            j.setPoint(30);
            deletePositionGhost(x - 1, y);
        }
        grid[x][y] = 1;
        grid[x - 1][y] = j.getIdPlayer();
        j.setPosition(new Point(x - 1, y));

        upMove(j, d - 1);
    }

    public void doMove(Player j, int d) {
        if (d == 0) return;
        int x = j.getPosition().x;
        int y = j.getPosition().y;
        if (x == height || grid[x + 1][y] == 4) return;
        if (grid[x + 1][y] == 3) {
            j.setGhostCaught(true);
            compteurGhosts++;
            j.setPoint(30);
            deletePositionGhost(x + 1, y);
        }
        grid[x][y] = 1;
        grid[x + 1][y] = j.getIdPlayer();
        j.setPosition(new Point(x + 1, y));

        doMove(j, d - 1);
    }

    public void leMove(Player j, int d) {
        if (d == 0) return;
        int x = j.getPosition().x;
        int y = j.getPosition().y;
        if (y == 0 || grid[x][y - 1] == 4) return;
        if (grid[x][y - 1] == 3) {
            j.setGhostCaught(true);
            compteurGhosts++;
            j.setPoint(30);
            deletePositionGhost(x, y - 1);
        }
        grid[x][y] = 1;
        grid[x][y - 1] = j.getIdPlayer();
        j.setPosition(new Point(x, y - 1));

        leMove(j, d - 1);
    }

    public void riMove(Player j, int d) {
        if (d == 0) return;
        int x = j.getPosition().x;
        int y = j.getPosition().y;
        if (y == width || grid[x][y + 1] == 4) return;
        if (grid[x][y + 1] == 3) {
            j.setGhostCaught(true);
            compteurGhosts++;
            j.setPoint(30);
            deletePositionGhost(x, y + 1);
        }
        grid[x][y] = 1;
        grid[x][y + 1] = j.getIdPlayer();
        j.setPosition(new Point(x, y + 1));

        riMove(j, d - 1);
    }

    /**
     * Gestion de déplacements des fantomes
     */

    public void ghostMove() {
        Random random = new Random();

        for (int i = 0; i < this.ghostPosition.length; i++) {
            int x = random.nextInt(this.height);
            int y = random.nextInt(this.width);
            while (grid[x][y] != 1) {
                x = random.nextInt(this.height);
                y = random.nextInt(this.width);
            }
            int a = ghostPosition[i].x;
            int b = ghostPosition[i].y;
            grid[a][b] = 1;
            grid[x][y] = 3;
            ghostPosition[i].setLocation(x, y);


        }
    }

    public void launchGhostMove()  {
        ScheduledExecutorService ghostMoved = Executors.newSingleThreadScheduledExecutor();
        Game laby = this;

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                laby.ghostMove();
                try {
                    ServerUDP.ghostMoved(ghostPosition, dso, ia);
                } catch (Exception e) {
                    System.out.println(" Ghost Stop moving ...");
                    
                }
            }
        };

        ghostMoved.scheduleAtFixedRate(task, 40, 40, SECONDS);
    }

    /**
     * place les joueurs uniquement si ce n'est pas deja fait
     */

    public void positionStart() {
        Random random = new Random();
        for (Player j : players) {
            if (j.getPosition().x != 0 && j.getPosition().y != 0) {
                return;
            }
            int x = random.nextInt(this.height);
            int y = random.nextInt(this.width);
            while (grid[x][y] != 1) {
                x = random.nextInt(this.height);
                y = random.nextInt(this.width);
            }
            grid[x][y] = j.getIdPlayer();
            j.setPosition(new Point(x, y));

        }
    }

    /**
     * Condition de fin de jeu
     * @return boolean
     */
    public boolean endGame() {
        return (compteurGhosts == ghosts);
    }

    /**
     * Met à null le point associe a un fantome
     * @param x  int
     * @param y  int
     */
    public void deletePositionGhost(int x, int y) {
        for (Point pos : ghostPosition) {
            if (pos != null && pos.getX() == x && pos.getY() == y) {
                pos = null;
                break;
            }
        }
    }

    /**
     * Création de la datagram socket et de l'inet socket address
     */
    public void newUDPsock() {
        String[] ipPort = Utils.getAddrIp();
        String ip = ipPort[0];
        String portm = ipPort[1];
        int portInt = Integer.parseInt(portm);
        try {
            DatagramSocket dso = new DatagramSocket();
            this.setDataSock(dso);
            InetSocketAddress ia = new InetSocketAddress(ip, portInt);
            this.setInSockAdress(ia);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

   
}
