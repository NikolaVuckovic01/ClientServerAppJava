package com.example.demo5;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Program extends Application{
    public static DataOutputStream dataOutputStream = null;
    public static DataInputStream dataInputStream = null;
    public static String putanja="";


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(500);
        stage.setHeight(350);
        stage.setTitle("Aplikacija");
        AnchorPane root=new AnchorPane();

        TextArea ta=new TextArea();
        AnchorPane.setTopAnchor(ta,40.0);
        AnchorPane.setLeftAnchor(ta,20.0);
        ta.setMaxHeight(30);
        ta.setMaxWidth(450);
        Label label1=new Label("Unesite tekst za provjeru rijeci da li je palindrom:");
        AnchorPane.setTopAnchor(label1,10.0);
        AnchorPane.setLeftAnchor(label1,20.0);
        Label label2=new Label("Odgovor servera:");
        label2.setTextFill(Color.RED);
        Label label5=new Label("");
        AnchorPane.setLeftAnchor(label5,115.0);
        AnchorPane.setTopAnchor(label5,130.0);
        AnchorPane.setTopAnchor(label2,130.0);
        AnchorPane.setLeftAnchor(label2,20.0);
        Button btn1=new Button("Provjeri");
        AnchorPane.setTopAnchor(btn1,90.0);
        AnchorPane.setLeftAnchor(btn1,20.0);
        btn1.setPrefWidth(200);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            Socket s = new Socket("127.0.0.1",8000);
            BufferedReader ulaz=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter izlaz=new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),true);
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!ta.getText().equals("")){
                    try {
                        String izlazz="PALINDROM:"+ta.getText();
                        izlaz.println(izlazz);
                        String odg2=ulaz.readLine();
                        label5.setText(odg2);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                    label2.setText("Popunite tekst polje!");
            }
        });

        ////***********************************************
        
        Label label3=new Label("Odgovor servera:");
        AnchorPane.setLeftAnchor(label3,20.0);
        AnchorPane.setTopAnchor(label3,240.0);
        label3.setTextFill(Color.RED);
        Label label4=new Label("");
        AnchorPane.setLeftAnchor(label4,115.0);
        AnchorPane.setTopAnchor(label4,240.0);
     
        //**************************************************

        Button izaberi=new Button("Izaberi");
        izaberi.setPrefWidth(200);
        AnchorPane.setTopAnchor(izaberi,200.0);
        AnchorPane.setLeftAnchor(izaberi,20.0);
        izaberi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc=new FileChooser();
                putanja= String.valueOf(fc.showOpenDialog(new Stage()));
                System.out.println(putanja);
            }
        });

        Button posalji=new Button("Posalji");
        posalji.setPrefWidth(200);
        AnchorPane.setTopAnchor(posalji,200.0);
        AnchorPane.setLeftAnchor(posalji,270.0);
        posalji.setOnAction(new EventHandler<ActionEvent>() {
            Socket socket = new Socket("127.0.0.1", 900);
            BufferedReader ulaz=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(putanja==""){
                    Alert a=new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Upozorenje!");
                    a.setContentText("Morate izabrati datoteku!");
                    a.show();
                }
                else {
                    try  {
                        sendFile(putanja);
                        String odgovor=ulaz.readLine();
                        label4.setText(odgovor);
                        label4.setTextFill(Color.GREEN);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        root.getChildren().addAll(label1,ta,btn1,label2,igrica,izaberi,posalji,label3,label4,label5);
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private static void sendFile(String path) throws IOException{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {

            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
}
