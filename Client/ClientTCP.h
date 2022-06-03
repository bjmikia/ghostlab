#ifndef GHOSTLAB_CLIENT_TCP_H
#define GHOSTLAB_CLIENT_TCP_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <time.h>
#include <pthread.h>

void * createOrsubscribeGame(int sock, char* sendd);
void * unsubscribeGame(int sock, char* sendd);
void* size_of_labyrinth(char* str);
void * askSize(int sock, char* sendd);
void * startGame(int sock, char* sendd);
void* receiveGamesAndOGames(int sock);
void* getPlayersList(int sock ,char* sendd);
char * getIPofUDP(char* mess);
char* getPortofUDP(char* mess);
void * startPlaying(int sock);
void * movePlayer(int sock, char* sendd);
void * quitGame(int sock, char* sendd);
void * client_tcp(void * s);
void * sendMessage(int sock, char* sendd);
void * duringGame(int sock);
#endif //GHOSTLAB_CLIENT_TCP_H
