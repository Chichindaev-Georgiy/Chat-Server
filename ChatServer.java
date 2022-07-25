import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer {
    private ArrayList<Client> clients= new ArrayList<>();
    private ServerSocket serverSocket;
    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    public void run()  {
        while(true) {
            System.out.println("Waiting...");

            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                clients.add(new Client(socket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAll(String message) {
        for (Client client: clients) {
            client.receive(message);
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();

    }
}
