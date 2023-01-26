package com.example.demo5;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server2 {

    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(900);
        while(true){
            Socket klijent=ss.accept();
            System.out.println("Sesija je pocela");
            ServerskaNit2 sn2=new ServerskaNit2(klijent);
        }
    }

}