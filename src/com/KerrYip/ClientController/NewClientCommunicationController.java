package com.KerrYip.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewClientCommunicationController {

    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    //private BufferedReader stdIn; //standard input

    public NewClientCommunicationController(String serverName, int portNumber){
        try {
            //create socket
            aSocket = new Socket(serverName, portNumber);

            //keyboard input stream
            //stdIn = new BufferedReader(new InputStreamReader(System.in));

            //Socket input stream
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter((aSocket.getOutputStream()), true);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String communicateStudentLogin(String instruction, String id){
        String response = "";

            try {
                System.out.println("Please enter a word: ");
                socketOut.println(instruction); //println is important, wont work otherwise
                System.out.println("Please enter a second word: ");
                socketOut.println(id); //println is important, wont work otherwise
                response = socketIn.readLine(); // read response from the socket
                System.out.println("Response is: " + response);
            }catch(IOException e){
                e.printStackTrace();
            }

        try{
            socketIn.close();
            socketOut.close();
        }catch(IOException e){
            e.getStackTrace();
        }

        return response;
    }

    public static void main(String[] args) throws IOException{
        NewClientCommunicationController myClient = new NewClientCommunicationController("localhost", 9898);
    }
}
