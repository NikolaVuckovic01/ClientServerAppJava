package com.example.demo5;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket ss=new ServerSocket(8000);
        while(true){
            Socket klijent=ss.accept();
            System.out.println("Sesija je pocela");
            ServerskaNit sn=new ServerskaNit(klijent);
        }
    }

}
