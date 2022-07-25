import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    private Socket socket;
    private String name;
    private Scanner in;
    private PrintStream out;
    private ChatServer server;

    public Client(Socket socket, ChatServer server){
        this.server = server;
        this.socket = socket;
        new Thread(this).start();
    }

    public void receive(String message) {
        out.println(message);
    }

    public void run() {
        while(true) {
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                in = new Scanner(is);
                out = new PrintStream(os);
                out.print("Enter your name: ");
                name = in.nextLine();
                out.printf("Welcome %s to the chat!%n", name);
                String input = in.nextLine();
                while (!input.equals("bye")) {
                    server.sendAll(String.format("%s: %s%n", name, input));
                    input = in.nextLine();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

    }
}