import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;

public class Client implements Runnable {
  private Socket clientSocket;
  private Thread thread;

  public Client(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    try (
        Socket socket = this.clientSocket;
        InputStream in = socket.getInputStream()) {
      StringBuilder request = new StringBuilder();
      int c;
      
      while ((c = in.read()) != -1) {
        request.append((char) c);
        if (in.available() == 0) {
          break;
        }
      }
      Request.parseRequest(request, socket);
    } catch (IOException ioe) {
      System.out.println("Error: " + ioe);
    }
  }

  public void go() {
    thread = new Thread(this);
    thread.start();
  }
}
