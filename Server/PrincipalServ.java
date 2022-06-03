package Server;
import Game.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class PrincipalServ {
    static List<Game> games = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8080);
            while (true) {
                Socket socket = server.accept();
                ServerTCP serv = new ServerTCP(socket, games);
                Thread t = new Thread(serv);
                t.start();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}