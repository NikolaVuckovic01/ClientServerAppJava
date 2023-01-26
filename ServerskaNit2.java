package com.example.demo5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class ServerskaNit2 implements Runnable{

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    PrintWriter izlaz;
    Socket Socket=null;

    ServerskaNit2(Socket soket) throws IOException {
        Socket=soket;
        dataInputStream = new DataInputStream(Socket.getInputStream());
        dataOutputStream = new DataOutputStream(Socket.getOutputStream());
        izlaz=new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())),true);
        this.run();
    }

    @Override
    public void run() {
        while(true){
            try {
                int bytes = 0;
                FileOutputStream fileOutputStream = new FileOutputStream("dat"+ System.currentTimeMillis()+".txt");

                long size = dataInputStream.readLong(); // read file size
                byte[] buffer = new byte[4 * 1024];
                while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
                    fileOutputStream.write(buffer, 0, bytes);
                    size -= bytes;
                }
                izlaz.println("Fajl uspesno poslat");
                dataOutputStream.flush();
                fileOutputStream.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
