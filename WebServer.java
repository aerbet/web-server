import java.net.ServerSocket;
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;

public class WebServer implements Runnable {
  private ServerSocket serverSocket;
  private Thread thread;
  private Object lock;

  public WebServer(int port) {
    try {
      serverSocket = new ServerSocket(port);
      lock = new Object();
      thread = new Thread(this);
    } catch (IOException ioe) {
      System.out.println("Server start error: " + ioe);
    }
  }

  private void startServer() {
    while (true) {
      try {
        Socket socket = serverSocket.accept();
        synchronized (lock) {
          Client client = new Client(socket);
          client.go();
        }
      } catch (IOException ioe) {
        System.out.println("Socket error: " + ioe);
      }
    }
  }

  public void run() {
    startServer();
  }

  public void go() {
    thread.start();
  }

  public void stopServer() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String command = scanner.nextLine();
      if (command.equals("stop")) {
        scanner.close();
        System.exit(0);
      }
    }
  }
}