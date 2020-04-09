package DebuggingCS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serverSocket;
	private Socket aSocket;
	private BufferedReader br;
	private PrintWriter pw;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is active on port: " + port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void runServer() {
		try {
			aSocket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connection with client established!");
		
		try {
			pw = new PrintWriter(aSocket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I/O connections established!");
		
		String input = "";
		while(!input.contentEquals("QUIT")) {
			try {
				System.out.println("Waiting for server to send input...");
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println("Server sees " + input);
		}
		
		System.out.println("Server connection closing");
	}
	
	public static void main(String [] args) {
		Server s = new Server(9090);
		s.runServer();
	}
	
}
