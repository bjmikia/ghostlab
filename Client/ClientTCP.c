#include "ClientTCP.h"
#include "ClientUDP.h"

int clientPort = 0;
/**
 * cette fonction reçoit REGOK m*** ou REGNO*** et parse & affiche le message en respectant le type
 * @param sock
 * @return
 */
void * rcvREGokREGno(int sock){
    //on reçoit, puis affiche le message du serveur
    char receive[100];
    int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    char type [7];
    strncpy(type,receive,6);
    type[6] = '\0';
    // on parse le message du serveur pour avoir le bon type ds l'affichage/afficher la bonne réponse
    if(strcmp(type, "REGOK ") == 0 ){
        uint8_t m;
        char nb[1];
        char first[7];
        char last[4];
        strncpy(first,receive,6);
        first[6]= '\0';
        strncpy(last,receive+7,3);
        last[3]='\0';
        memmove(nb,receive+6,1);
        m = atoi(nb);

        printf("Server sent: %s%u%s\n",first,m,last);

    }else{
        printf("Server sent: %s\n",receive);
    }
    return NULL;
}

/**
 *  [REGIS␣id␣port␣m***]
 * reception: [REGOK␣m***] or [REGNO***]
 */

void * subscribeGame(int sock, char* buf){
    uint8_t m;
    
    char* first= malloc(sizeof (char)*21);
    char* sendd= malloc(sizeof (char)*25);
    char port[5];
    strncpy(first,buf,20);
    memmove(sendd,first, 21);
    memmove(port,buf+15,4);
    port[4]='\0';
    clientPort = atoi(port);
    int number=atoi(buf+20);
    m = (uint8_t) (number);
    memmove(sendd+20,&m, 1);
    strcat(sendd,"***");

    int size_sent=send(sock,sendd, strlen(sendd),0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }

    printf("client sent: %s%u***\n",first,m);

    //on reçoit, puis affiche le message du serveur
    rcvREGokREGno(sock);
    free(first);
    return NULL;
}

/**
 * envoie : [NEWPL␣id␣port***]
 * reception: [REGOK␣m***] or [REGNO***]
 */
void * createGame(int sock,char* sendd){
    char port[5];
    memmove(port,sendd+15,4);
    port[4]='\0';
    clientPort = atoi(port);
    int size_sent= send(sock,sendd, strlen(sendd),0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n",sendd);
    rcvREGokREGno(sock);
    return NULL;

}


/**
 * cette fonction reçoit UNROK m*** ou DUNNO*** et parse & affiche le message en respectant le type
 * @param sock
 * @return
 */
void * rcvUNRokDUNNO(int sock){
    //on reçoit, puis affiche le message du serveur
    char receive[100];
    int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    char type [6];
    strncpy(type,receive,6);
    // on parse le message du serveur pour avoir le bon type ds l'affichage/afficher la bonne réponse
    if(strcmp(type, "UNRGOK ") == 0 ){
        uint8_t m;
        char* first= malloc(sizeof (char)*6);
        char* last=malloc(sizeof (char)*3);
        strncpy(first,receive,6);
        strncpy(last,receive+7,3);
        memmove(&m,receive+6,sizeof (uint8_t));
        printf("Server sent: %s%u%s\n",first,m,last);
        free(first);
        free(last);

    }else{
        printf("Server sent: %s\n",receive);
    }
    return NULL;
}

/**
 * envoie : [UNREG***]
 * reception: [UNROK␣m***] ou  [DUNNO***]
 */
void * unsubscribeGame(int sock, char* sendd){
    int size_sent= send(sock, sendd, strlen(sendd), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n",sendd);
    //on reçoit, puis affiche le message du serveur
    rcvUNRokDUNNO(sock);
    return NULL;
}

/** fonction auxilaire  qui convertit h et w et donne le bon affichage au message [SIZE!␣m␣h␣w***] **/
void* size_of_labyrinth(char* str){

    char* rep= malloc (sizeof(char)*6);
    uint8_t m;
    uint16_t h;
    uint16_t w;

    memmove(rep,str,6);
    int tmp = atoi(str+6);
    m = (uint8_t) tmp;
    tmp = atoi(str+8);
    h = (uint16_t) tmp;
    tmp = atoi(str+12);
     w = (uint16_t) tmp;
    printf("%s%u %u %u***",rep,m,ntohs(h),ntohs(w));
    free(rep);
    return NULL;
}

/**
 * envoie:  [SIZE?␣m***] ou [LIST?␣m***]
 *
 */
void * sendListmOrSizem(int sock, char* sendd){
    uint8_t m;
    char* first= malloc(sizeof (char)*6);
    char* last="***"; // on remplit les 3 étoiles à la fin
    char* buf=malloc(sizeof (char)*11);
    strncat(first,sendd,6);
    memmove(buf,first,6);
    int number=atoi(sendd+6);
    m = (uint8_t) (number);
    memmove(buf+6,&m,1);
    strcat(buf,last);
    int size_sent= send(sock, buf, strlen(buf), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s%u%s\n",first,m,last);
    free(first);
    free(buf);
    
    return NULL;
}
/**
 * envoie:  [SIZE?␣m***]
 * reception:  [SIZE!␣m␣h␣w***] ou  [DUNNO***]
 */
void * askSize(int sock, char* sendd){
    char receive[100];
    sendListmOrSizem(sock,sendd);
    int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
    
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';

    if(strcmp(receive,"DUNNO***")==0) {
        printf("Server sent: %s\n", receive);
    }else {
        printf("Server sent: ");
        size_of_labyrinth(receive);
        printf("\n");

    }
    return NULL;
}

/**
 * envoie:  [START***] ensuite bloque (pas de rcv)
 */
void * startGame(int sock, char* sendd){
    // on envoie le message start et on bloque
    int size_sent= send(sock, sendd, strlen(sendd), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n",sendd);
    return NULL;
}

/**
 * Ce que le serveur envoie tout au début du jeu
 * reception:  [GAMES␣n***]
 * puis n fois [OGAME␣m␣s***]
 * */
void* receiveGamesAndOGames(int sock){

    char receive[100];
    char number [1];
    char mess[100];
    // on reçoit [GAMES␣n***]
    int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    printf("Server sent: %s\n",receive);
    strncpy(number,receive+6,1);
    int n = atoi(number);
    // on reçoit n fois [OGAME␣m␣s***]
    for (int i=0;i<n;i++){
        size_rec= recv(sock, mess, 99 * sizeof(char), 0);
        if(size_rec==-1){
            perror("error receiving");
            exit(EXIT_FAILURE);
        }
        mess[size_rec]= '\0';
        printf("%s\n",mess);

    }
    return NULL;

}

/**
 * envoie : [GAME?***]
 * reception:  [GAMES␣n***]  puis n fois  [OGAME␣m␣s***]
 */
void* receiveGamesAndOGames2(int sock, char * sendd){

    char receive[11];
    char mess[13];
    char number [1];
    //on send [GAME?***]
    int size_sent= send(sock, sendd, strlen(sendd), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n",sendd);

    // on reçoit [GAMES␣n***]
    int size_rec= recv(sock, receive, 10 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    printf("Server sent: %s\n",receive);
    strncpy(number,receive+6,1);
    int n = atoi(number);
    // on reçoit n fois [OGAME␣m␣s***]
    for (int i=0;i<n;i++){
        size_rec= recv(sock, mess, 99 * sizeof(char), 0);
        if(size_rec==-1){
            perror("error receiving");
            exit(EXIT_FAILURE);
        }
        mess[size_rec]= '\0';
        printf("%s\n",mess);

    }
    return NULL;
}


/**
 * envoie : [GLIS?***]
 * reception:  [GLIS!␣s***]  puis s fois [GPLYR␣id␣x␣y␣p***]
 */

void* getPlayersListDuringGame(int sock ,char* sendd){
    // on evoie GLIS?***
    int size_sent= send(sock, sendd, strlen(sendd), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent : %s\n",sendd);

    char receive[11];
    char msj[33];
    // On reçoit GLIS! s***
    int size_rec= recv(sock, receive, 10 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';

        uint8_t s;
        char rep[7];
        memmove(rep,receive,6);
        rep[6]='\0';
        int number=atoi(receive+6);
        s = (uint8_t) (number);

        printf("Server sent: %s%u***\n",rep,s);
        for (int i=0;i<s;i++) {
            size_rec = recv(sock, msj, 32 * sizeof(char), 0);
            if (size_rec == -1) {
                perror("error receiving");
                exit(EXIT_FAILURE);
            }
            msj[size_rec] = '\0';
            printf("%s\n", msj);
        }

    return NULL;
}



/**
 * envoie : [LIST?␣m***]
 * reception:  [LIST!␣m␣s***]  puis s fois [PLAYR␣id***]  ou [DUNNO***]
 */
void* getPlayersList(int sock ,char* sendd){
    sendListmOrSizem(sock,sendd);
    char receive[13];
    char msj[18];

    int size_rec= recv(sock, receive, 12 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    // if strcmp receive , "DUNNO" on fait rien sinon on execute le code ci dessus
    if(strcmp(receive,"DUNNO***")==0) {
        printf("Server sent: %s\n", receive);
    }else {
        printf("Server sent: \n");
        uint8_t m;
        uint8_t s;
        char rep[7];
        memmove(rep,receive,6);
        rep[6]='\0';   
        int number=atoi(receive+6);
        m = (uint8_t) (number);   
        int number2=atoi(receive+8);
        s = (uint8_t) (number2);

        printf("%s%u %u***\n",rep,m,s);
        for (int i=0;i<s;i++) {
            size_rec = recv(sock, msj, 17 * sizeof(char), 0);
            if (size_rec == -1) {
                perror("error receiving");
                exit(EXIT_FAILURE);
            }
            msj[size_rec] = '\0';
            printf("%s***\n", msj);
        }
    }

    return NULL;
}

/**
 * fonction auxilaiire qui récupère l'adresse ip dans le message [WELCO␣m␣h␣w␣f␣ip␣port***]
 * @param mess
 * @return ip
 */
char * getIPofUDP(char* mess){
    char* ip = malloc(sizeof(char)*16);
    memmove(ip,mess+20,15);
    ip[15]='\0';
   
    return ip;
}
/**
 * fonction auxilaiire qui récupère le port dans le message [WELCO␣m␣h␣w␣f␣ip␣port***]
 * @param mess
 * @return port
 */
char* getPortofUDP(char* mess){
    char *port = malloc(sizeof(char)*5) ;
    memmove(port,mess+36,4);
    port[4]= '\0';

    return port;
}


/**
 * reçoit le message [WELCO␣m␣h␣w␣f␣ip␣port***] après le start et déclenche le jeu
 * @param sock
 * @return
 */
void * startPlaying(int sock) {
    char receive1 [100];
    char receive2 [100];
    char rep[7];
    char* ip ;
    char* port ;
    uint8_t m,f;
    uint16_t h,w;

    // on reçoit  [WELCO␣m␣h␣w␣f␣ip␣port***]
    int size_rec= recv(sock, receive1, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive1[size_rec]= '\0';
    //printf("Server sent avant parsing : %s\n", receive1);
    memmove(rep,receive1,6);
    rep[6]='\0';
    int tmp = atoi(receive1+6);
    m = (uint8_t) tmp;

    tmp = atoi(receive1+8);
    h = (uint16_t) tmp;

    tmp = atoi(receive1+12);
     w = (uint16_t) tmp;

    tmp = atoi(receive1+17);
     f = (uint8_t) tmp;

    ip= getIPofUDP(receive1);
    port= getPortofUDP(receive1);
    printf("Server sent: %s%u %u %u %u %s %s***\n", rep,m,ntohs(h),ntohs(w),f,ip,port);

    // on reçoit  [POSIT␣id␣x␣y***]
    size_rec= recv(sock, receive2, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive2[size_rec]= '\0';
    printf("Server sent: %s\n", receive2);
    

     /*************************************************************
   *  création de la socket pour la communication en multidifusion *
   **************************************************************/
   int *arg = malloc(sizeof(*arg));
   int *arg1 = malloc(sizeof(*arg1));
    int sock_multi=socket(PF_INET,SOCK_DGRAM,0);
    int ok=1;
    int r=setsockopt(sock_multi,SOL_SOCKET,SO_REUSEPORT,&ok,sizeof(ok));
    if(r==-1){
        perror("error setsockopt udp1");
        exit(EXIT_FAILURE);
    }
    struct sockaddr_in address_sock;
    address_sock.sin_family=AF_INET;
    address_sock.sin_port=htons(atoi(port));
    address_sock.sin_addr.s_addr=htonl(INADDR_ANY);
    r=bind(sock_multi,(struct sockaddr *)&address_sock,sizeof(struct sockaddr_in));
    if(r==-1){
        perror("error bind udp");
        exit(EXIT_FAILURE);
    }
    struct ip_mreq mreq;
    mreq.imr_multiaddr.s_addr=inet_addr(ip);
    mreq.imr_interface.s_addr=htonl(INADDR_ANY);
    r=setsockopt(sock_multi,IPPROTO_IP,IP_ADD_MEMBERSHIP,&mreq,sizeof(mreq));
    if(r==-1){
        perror("error setsockopt udp2");
        exit(EXIT_FAILURE);
    }
    /*************************************************************
   *  création de la socket pour la reception en udp *
   **************************************************************/
    int sock_udp=socket(PF_INET,SOCK_DGRAM,0);
    struct sockaddr_in address_sock2;
    address_sock2.sin_family=AF_INET;
    address_sock2.sin_port=htons(clientPort);
    address_sock2.sin_addr.s_addr=htonl(INADDR_ANY);
    r=bind(sock_udp,(struct sockaddr *)&address_sock2,sizeof(struct sockaddr_in));
    if(r==-1){
        perror("error bind udp2");
        exit(EXIT_FAILURE);
    }

    *arg = sock_multi;
    *arg1 = sock_udp;

    pthread_t th;
    pthread_t th1;
    
    pthread_create(&th, NULL, client_receive_multi, arg);
    pthread_create(&th1, NULL, client_receive_udp, arg1);
    return NULL;
}
/**
 * fonction qui envoie l'action du mouvement et reçoit la réponse du serveur
 * @param sock
 * @param sendd
 * @return
 */
void * movePlayer(int sock, char* sendd){
    char receive[100];
    // on envoie la direction
    int size_sent= send(sock, sendd, strlen(sendd), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n",sendd);
    // on reçoit la réponse du serveur
    int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    printf("Server sent: %s\n", receive);

    return NULL;
}

void * sendMessage(int sock, char* sendd){
    char receive[100];
    // on envoie le message
    int size_sent= send(sock, sendd, strlen(sendd), 0);
    if(size_sent==-1){
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n",sendd);
    // on reçoit la réponse du serveur
    int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
    if(size_rec==-1){
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec]= '\0';
    printf("Server sent: %s\n", receive);

    return NULL;
}


void * quitGame(int sock, char* sendd) {
    char receive[100];
    // on envoie [IQUIT***]
    int size_sent = send(sock, sendd, strlen(sendd), 0);
    if (size_sent == -1) {
        perror("error sending");
        exit(EXIT_FAILURE);
    }
    printf("client sent: %s\n", sendd);
    // on reçoit la réponse du serveur
    int size_rec = recv(sock, receive, 99 * sizeof(char), 0);
    if (size_rec == -1) {
        perror("error receiving");
        exit(EXIT_FAILURE);
    }
    receive[size_rec] = '\0';
    printf("Server sent: %s\n", receive);
    // le serveur ferme la connection :
    close(sock);
    return NULL;
}


/**
 * fonction principale durant la partie
 * @param sock
 * @return
 */
void * duringGame(int sock){

    while(1){

        char send[255];
        char* type= malloc (sizeof(char)*6) ;
        memset(send,0,sizeof(send));
        memset(type,0,5);

        // on lit ce que le client tape sur le terminal 
        int rd = read(STDIN_FILENO,send,99);
        if(rd == -1){
            perror("error read ");
            exit(EXIT_FAILURE);
        }
        send[rd]='\0';
        strncat(type,send,5);
        printf("type: %s\n",type);

        if (strcmp(type, "UPMOV") == 0 || strcmp(type, "DOMOV") == 0
                     || strcmp(type, "LEMOV") == 0  || strcmp(type, "RIMOV") == 0){
                movePlayer(sock,send);
               

        }
        else if (strcmp(type, "GLIS?") == 0 ){
             getPlayersListDuringGame(sock,send);
            

        }else if (strcmp(type, "MALL?") == 0  || strcmp(type, "SEND?") == 0 ){
                sendMessage(sock, send);
            

        }else if(strcmp(type, "IQUIT") == 0 ){
             quitGame(sock, send);
             return NULL;
        }else{
            continue;
        }
    }
    return NULL;
}

/***********************************************/

/**
 * fonction appelé par le thread qui s'occupe de la communication tcp et qui commence le jeu
 * @param s
 * @return
 */

void * client_tcp(void * s){
     int sock = *((int *) s);
    
    receiveGamesAndOGames(sock);    // recevoir tout d'abord les messages du serveur dès qu'on se connecte

    while(1){

        char send[100];
        char* type= malloc (sizeof(char)*6) ;
        memset(send,0,sizeof(send));
        memset(type,0,5);

        // on lit ce que le client tape sur le terminal
        int rd = read(STDIN_FILENO,send,99);
        if(rd == -1){
            perror("error read ");
            exit(EXIT_FAILURE);
        }
        send[rd]='\0';
        strncat(type,send,5);
       


        if (strcmp(type, "NEWPL") == 0 ){
            createGame(sock,send);
            

        }else if ( strcmp(type, "REGIS")==0 ) {
            subscribeGame(sock,send);

        }
        else if (strcmp(type, "UNREG") == 0 ){
            unsubscribeGame(sock,send);
            
        }
        else if (strcmp(type, "SIZE?") == 0 ){
            askSize(sock,send);
           

        }
        else if (strcmp(type, "LIST?") == 0 ){
            getPlayersList(sock,send);
           

        }
        else if (strcmp(type, "GAME?") == 0 ){
            receiveGamesAndOGames2(sock,send);
            

        }
        else if  (strcmp(type, "START") == 0 ){

            // Une fois que la partie commence :

            // chaque joueur envoie START*** et on bloque
            startGame(sock,send);
            // une fois que tous les joueurs ont envoyé START*** on reçoit WELCOME... on appelle un thread pour l'udp
            startPlaying(sock);
            
            break;
        }else{
            char receive [100];
            int size_rec= recv(sock, receive, 99 * sizeof(char), 0);
            if(size_rec==-1){
                perror("error receiving");
                exit(EXIT_FAILURE);
            }
            receive[size_rec]= '\0';
            printf("Server sent: %s\n", receive);
            continue;

        }

    }
    duringGame(sock);
    return NULL;
}
