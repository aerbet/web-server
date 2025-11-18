import java.net.Socket;
import java.net.URLDecoder;
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

      parseRequest(request, socket);
    } catch (IOException ioe) {
      System.out.println("Error: " + ioe);
    }
  }

  public void go() {
    thread = new Thread(this);
    thread.start();
  }

  public void parseRequest(StringBuilder request, Socket socket) {
    try {
      String httpRequest = request.toString();
      String firstLine = httpRequest.split("\r\n")[0];
      String[] requestParts = firstLine.split(" ");

      if (requestParts.length < 2) {
        NotFoundPage.sendNotFoundPage(socket);
        return;
      }

      String method = requestParts[0];
      String path = requestParts[1];

      if (method.equals("POST") && path.equals("/login")) {
        String[] parts = httpRequest.split("\r\n\r\n", 2);
        String username = null;
        String password = null;
        String body;

        if (parts.length > 1) {
          body = parts[1];
        } else {
          body = "";
        }

        System.out.println("=== BODY ===");
        System.out.println(body);

        String[] params = body.split("&");

        for (String param : params) {
          String[] kv = param.split("=");
          if (kv.length == 2) {
            String key = URLDecoder.decode(kv[0], "UTF-8");
            String value = URLDecoder.decode(kv[1], "UTF-8");
            if ("username".equals(key)) {
              username = value;
            }
            if ("password".equals(key)) {
              password = value;
            }
          }
        }

        if ("admin".equals(username) && "1234".equals(password)) {
          WelcomePage.sendWelcomePage(socket);
        } else {
          ErrorPage.sendErrorPage(socket, "Invalid username or password");
        }

      } else if (method.equals("GET") && (path.equals("/") || path.equals("/login"))) {
        LoginPage.sendLoginPage(socket);
      } else {
        NotFoundPage.sendNotFoundPage(socket);
      }
    } catch (IOException ioe) {
      System.out.println("Error: " + ioe);
    }
  }
}
