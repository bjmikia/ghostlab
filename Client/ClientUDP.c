#include "ClientUDP.h"

/** méthode pour recevoir les messages multidifusés des autres joueurs**/
void * client_receive_multi(void * s1) {

        int sock = *((int *) s1);
        char receive[255];
        int rec;
        while ((rec=recv(sock,receive,254,0))> 0){
        receive[rec]='\0';
        printf("Message multi recu : %s\n",receive);
        }
    return NULL;
}

/** méthode pour recevoir les messages privé en udp des autres joueurs**/
void * client_receive_udp(void * s1) {
        int sock = *((int *) s1);
        char receive[255];
        int rec;
        while ((rec=recv(sock,receive,254,0))> 0){
        receive[rec]='\0';
        printf("Message udp recu : %s\n",receive);
        }
    return NULL;
}
