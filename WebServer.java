import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class WebServer {
    private ServerSocket serverSocket;

    public WebServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch(IOException ioe) {
            System.out.println("Server start error: " + ioe);
        }
    }

    public void startServer() {
        try {
            Socket socket = serverSocket.accept();
            System.out.println(socket);
        } catch(IOException ioe) {
            System.out.println("Server start error: " + ioe);
        }
    }
}
