package DebuggingCS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {

    private Socket aSocket;
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;

    public ServerExample(int port) {
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Logic of the application
    public void capitalize() {
        try {
            aSocket = serverSocket.accept();
            System.out.println("Connection accepted by server!");
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter((aSocket.getOutputStream()), true);
        }catch(IOException e){
            e.printStackTrace();
        }


        String line = null;
        String line2 = null;

        while(true){
            try{
                line = socketIn.readLine();
                line2 = socketIn.readLine();
                if(line.equals("QUIT")){
                    line = "Good Bye!";
                    socketOut.println(line);
                    break;
                }
                line = line.toUpperCase();
                line2 = line2.toUpperCase();
                socketOut.println(line + line2);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main (String [] args) throws IOException {
        try {
            ServerExample myServer = new ServerExample(9898);
            myServer.capitalize();

            myServer.socketIn.close();
            myServer.socketOut.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
