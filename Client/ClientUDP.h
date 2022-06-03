#ifndef GHOSTLAB_CLIENT_UDP_H
#define GHOSTLAB_CLIENT_UDP_H

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
void * client_receive_udp(void * s1);
void *client_receive_multi(void * s1);
#endif //GHOSTLAB_CLIENT_UDP_H
