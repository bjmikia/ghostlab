

------------------------------------------------ Présentation ------------------------------------------------


L’objectif de ce projet est de réaliser un serveur permettant de jouer à un jeu : Ghostlab. Un client doit pouvoir créer un nouveau joueur,créer une nouvelle partie ou en rejoindre une mais également d'interroger le serveur sur les caracteristiques d'une partie. Le serveur doit lancer la partie quand tous les joueurs sont prêts et les joueurs peuvent jouer en écrivant des commandes au serveur. Le but étant d'avoir le plus de points en attrapant des fantomes dans un labyrinthe à l'aveugle. Le deuxieme objectif est de créer un clien type pour le serveur.


------------------------------------------------ Commandes de lancement ------------------------------------------------


Pour compiler : make

Pour exécuter le serveur : 
			java -classpath bin Server.PrincipalServ

Pour exécuter le client : 
			./client localhost 8080


------------------------------------------------ Fonctionnalités ------------------------------------------------



Notre serveur respecte rigoureusement le protocole donné, le labyrinthe est affiché sur le terminal qui a lancé le serveur avec des symboles ASCII.





------------------------------------------------ Architecture ------------------------------------------------

PrincipalServ.java : On a le serveur principal qui accepte les connexions et créés les Threads pour la communication TCP.
ServerTCP.java : Pour la communication TCP entre le serveur et le client à l'aide des fonctions dans AuxFunctions.java.
ServerUDP.java : Classe statique qui s'occupe de la communication UDP.

Client.c : Se connecte au serveur et créé deux threads, un pour la connection TCP et un autre pour l'UDP.
ClientTCP.c : Contient les fonctions pour communiquer en TCP avec le serveur et afficher ce que le serveur envoie.
ClientUDP.c : Contient les fonctions pour recevoir les paquets UDP envoyé par le serveur en multicast.

Game.java : Contient le squelette du jeu, et est classe mère de 3 labyrinthes ( qui sont aussi des games).
Player.java : Contient les informations sur un joueur.

Utils.java : Contient les fonctions auxilières réutilisées plusieurs fois telles que les fonctions de vérification de port ou bien l'affectation des adressesIP.


------------------------------------------------ Auteurs ------------------------------------------------
Groupe 30: 

NODIN Aurélie

MIKIA Benidy

NASR Amira


------------------------------------------------ Répartition des tâches ------------------------------------------------
    Amira : Implèmentation du Client.c :
                        établie la connection avec la serveur (TCP et UDP)
            Implèmentation du ClientTCP.c :
                        contient les fonctions auxiliaire de communication en TCP avec le serveur 
            Implèmentation du ClientUDP.c :
                        contient les fonctions auxiliaire de communication en UDP avec les joueurs 
            Implèmentation de ServerUDP.java:
                        contient les fonctions auxiliaire de communication en UDP avec les joueurs 


  Aurélie : Implèmentation de ServeurTCP.java:
                    contient la fonction principale run ainsi 
                    que des fonctions auxiliaires
            Implèmentation du AuxFunctions.java :
                    contient les fonctions auxiliaires de communication avec le client 
            Implèmentation de PrincipalServ.java :
                    lancement des threads
                    
            Implèmentation de Utils.java     

    
    Benidy : Implèmentation du jeu (Game) :
                    Structure et représentation du jeu avec les labyrinthes, les joueurs(Player.java) 
                    et le déroulement d'une partie(Game.java)

            Implémentation du Serveur :
                    ServeurTCP.java
                    AuxFunctions.java

            Implèmentation de Utils.java



    

    ===> après avoir réuni notre travail , chaque membre a participé au déploiment 
        des tests pour faire fonctionner le jeu (correction code / modification des implèmentations ..etc).




------------------------------------------------ Conclusion ------------------------------------------------
Nous avons pu employer tout ce que nous avons appris en C et JAVA pendant ce semestre, le cours Programmation Réseaux ayant été d'une
importance particulière. En effet, il nous a permis de comprendre les bases du travail en groupe avec les systèmes de contrôle de
versions (git).
Dans l'ensemble, il y avait une bonne coordination dans les rôles de chacun et une bonne entente au sein de l'équipe était présente
