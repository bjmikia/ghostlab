.PHONY binary: serveur
all:  serveur 

serveur:
	javac -d bin ./Game/*.java ./Server/*.java ./Utils/*.java




.PHONY binary: client
client:
	gcc -pthread -Wall  -I ./Client -o client ./Client/Client.c ./Client/ClientTCP.c ./Client/ClientUDP.c

clean :
	        rm -rd bin		
		rm -f client


