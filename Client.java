import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;

public class Client implements Runnable {
    private Socket clientSocket;
    private Thread thread;

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
        thread = new Thread(this);
    }

    public void run() {
        System.out.println("Proccessing client.");
        System.out.println(clientSocket);
        InputStream in = null;
        try {
            in = clientSocket.getInputStream();
        } catch(IOException ioe) {
            System.out.println("Error into InputStream: " + ioe);
        }

        while(true) {
            try {
                if (in != null) {
                    int unicode = in.read();
                    if (unicode == -1) {
                        System.out.println("Server did not respond");
                        break;
                    }
                    char symbol = (char)unicode;
                    System.out.print(symbol);
                }
            } catch(IOException ioe) {
                System.out.println("Error: " + ioe);
            }
        }
    }

    public void go() {
        thread.start();
    }
}
