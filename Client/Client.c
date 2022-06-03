#include "Client.h"

int main(int argc, char** argv ) {
    if (argc!=3){
        fprintf(stderr,"You entered less arguments than expected!\n"
                       "Please retry with the correct number of arguments\n");
        exit(EXIT_FAILURE);
    }
    char * adress= argv[1];
    char * port = argv[2];

    /*************************************************************
     *     création de la socket pour la communication en tcp    *
     *************************************************************/
    struct addrinfo *first_info;
    struct addrinfo hints;
    memset(&hints, 0, sizeof hints);
    hints.ai_family = AF_INET; 
    int n=getaddrinfo(adress,port,&hints,&first_info);
    if (n !=0) {
        perror("erreur getaddrinfo");
        exit(EXIT_FAILURE);
    }
    struct addrinfo *info=first_info;
    int found=0;
    struct sockaddr_in *addressin;
    while(info!=NULL && found==0){
      addressin=(struct sockaddr_in *)info->ai_addr;
      found=1;
      info=info->ai_next;
    }
    if(found != 1){
        perror("server address recovery error");
        exit(EXIT_FAILURE);
    }
    int sock_tcp = socket(PF_INET, SOCK_STREAM, 0);
    int r = connect(sock_tcp, (struct sockaddr *) addressin, sizeof(struct sockaddr_in));
    if (r == -1) {
        perror("error connect");
        exit(EXIT_FAILURE);
    }

    //reception/envoie tcp
    pthread_t th1;
    pthread_create(&th1, NULL, client_tcp, &sock_tcp);
    
    // attendre l'exécution du thread
    void *retval;
    pthread_join(th1, &retval);

    if(close(sock_tcp)==-1){
        exit(EXIT_FAILURE);
    }
    return 0;
}
