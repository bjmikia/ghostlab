package Utils;
import Game.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils {
   
 public static HashMap<String[],Boolean> addrips = new HashMap<>();
  
    // Création de la map contenant les adresses et les ports de multidifusions
  public static String[] getAddrIp(){
      String [] res = null;
      synchronized(addrips){
        if(addrips.isEmpty()){
            String[] ipport1= {"225.230.124.220","3443"};
            String[] ipport2= {"225.127.222.254","6226"};
            String[] ipport3= {"225.255.123.123","4554"};
            String[] ipport4= {"225.127.255.124","5885"};
            String[] ipport5= {"225.246.126.255","7117"};
            String[] ipport6= {"225.240.240.230","8998"};
    
            addrips.put(ipport1,false);
            addrips.put(ipport2,false);
            addrips.put(ipport3,false);
            addrips.put(ipport4,false);
            addrips.put(ipport5,false);
            addrips.put(ipport6,false);
        
        }
        
        for (Map.Entry<String[],Boolean> m : addrips.entrySet()) {
            if(m.getValue().equals(false) ){
                 m.setValue(true);
                 res = m.getKey();
                return res;
            }
        }
    }
    return res;
  }
  /**
   * Set l'adresse de multidifusion d'une partie
   * @param ia
   * @return
   */
  public static boolean setAddrIp(InetSocketAddress ia){
    String ip =ia.getAddress().getHostAddress();
    String port = Integer.toString(ia.getPort());
    for (Map.Entry<String[],Boolean> m : addrips.entrySet()) {
        String [] res = m.getKey();
        if(res[0]== ip && res[1]== port && m.getValue().equals(true) ){
            m.setValue(false);
            return true;

        }
    }
    return false;

  }

  /**
   * Met un int sous forme de string au bon format selon comment il doit etre envoyé
   * @param n la taille finale de la chaine
   * @param st le nombre à convertir
   * @param c la chaine de remplissage
   * @return
   */
  //
  public static String goodFormat(int n, int st,String c){
    String newst = Integer.toString(st);
    while(newst.length() != n){
        newst = c+newst;
    }
    return newst;
 }

 /**
  * Retourne la valeur en entier de la chaine, 0 si la chaine n'est pas un entier
  * @param number l'entier sous forme de string envoyé par le client 
  * @return un entier 
  */
 public static int goodMove(String number){
    int newNum = 0;
    try{
        number = number.trim();
        newNum = Integer.parseInt(number);
    }catch(NumberFormatException e){
        System.out.println("Invalid move !");
    }
     return newNum;
 }

  /**
     * Fonction utile pour les LEMOV DOMOV etc pour completer par des 0 si l'utilisateur envoie 2 au lieu de 002 par exemple
     * @param c char
     * @param n int
     * @param s String
     * @return String
     */
    public static String fill(char c, int n, String s){
        String res = s;
        for(int i = s.length(); i < n; i++ ){
            res = c +""+ res;
            System.out.println(res);
        }
        return res;
    }


    


    public static String correctName(String name) {
      if (name.length() < 8) {
          String res = name;
          int ref = 8 - name.length();
          for (int i = 0; i < ref; i++) {
              res = res + "0";
          }
          return res;
      } else if (name.length() > 8) {
          char[] tmp = name.toCharArray();
          String res = "";
          for (int i = 0; i < 8; i++) {
              res = res + tmp[i];
          }
          return res;
      } else {
          return name;
      }
    }

    /**
     * Vérifie s'il n'y a pas déjà un joueur avec le port ou le nom passés en paramètre
     * @param port le port du client 
     * @param id le pseudo du client 
     * @param g la partie 
     * @return
     */
    public static boolean isCorrectPortAndName(String port,String id,Game g){
        for(Player p: g.getPlayers()){
            if(p.getPort().equals(port)||p.getName().equals(correctName(id))){
                return false;
            } 
        }
            
        return isCorrectPort(port);
    }

    /**
     * Teste si le port est de longueur 4 ou 5 
     * @param port
     * @return
     */
    public static boolean isCorrectPort(String port){     
        return port.matches("\\d{4,5}");
    }

    public  static int  ourRandom(int n ){
        Random random = new Random();
        int x = random.nextInt(n);
         return x=x+1;
    }

    public static String takeMessage (InputStream br )throws IOException{
        br.read();
        String res ="";
        int n;
        do{
            n = br.read();
            res = res + (char) n;
        } while((char)n !='*');
        res = res.replace("*","");
        return res;
    }


}
