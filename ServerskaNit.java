package com.example.demo5;
import javafx.scene.control.TextArea;
import java.io.*;
import java.net.Socket;

public class ServerskaNit implements Runnable{
    Socket soket;
    BufferedReader ulaz;
    PrintWriter izlaz;
    TextArea ta;


    public ServerskaNit(Socket s) throws Exception {
        this.soket=s;
        ulaz=new BufferedReader(new InputStreamReader(soket.getInputStream()));
        izlaz=new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())),true);
        this.run();

    }
    @Override
    public void run() {
        while (true){
            String mes=null;
            try {
                mes=ulaz.readLine();
                if(mes.contains("PALINDROM")){
                    System.out.println(mes);
                    String[] niz=mes.split(":");
                    String odg=niz[1];
                    odg=odg.toLowerCase();
                    String[] odg2=odg.split(" ");
                    String string="";
                    for(int i=0;i<odg2.length;i++){
                        string+=odg2[i];
                    }
                    String string2="";
                    for (int j=string.length()-1;j>=0;j--){
                        string2+=string.charAt(j);
                    }
                    String odgovor="";
                    if(string2.equals(string))
                        odgovor="Tekst "+odg+" je palindrom";
                    else
                        odgovor="Tekst "+odg+" nije palindrom";
                    izlaz.println(odgovor);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
