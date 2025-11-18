import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;

public class Request {
  private static final UserStorage userStorage = new UserStorage();

  private Request() {
  }

  public static void parseRequest(StringBuilder request, Socket socket) {
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

      String body = "";
      if (method.equals("POST")) {
        String[] parts = httpRequest.split("\r\n\r\n", 2);
        if (parts.length > 1) {
          body = parts[1];
        }
      }

      if (method.equals("POST") && path.equals("/login")) {
        String username = null;
        String password = null;

        String[] params = body.split("&");
        for (String param : params) {
          String[] kv = param.split("=");
          if (kv.length == 2) {
            String key = URLDecoder.decode(kv[0], "UTF-8");
            String value = URLDecoder.decode(kv[1], "UTF-8");
            if ("username".equals(key)) {
              username = value;
            } else if ("password".equals(key)) {
              password = value;
            }
          }
        }

        if (userStorage.authenticate(username, password)) {
          WeatherPage.sendWeatherPage(socket);
        } else {
          ErrorPage.sendErrorPage(socket, "Invalid username or password");
        }
      } else if (method.equals("POST") && path.equals("/register")) {
        String username = null;
        String password = null;

        String[] params = body.split("&");
        for (String param : params) {
          String[] kv = param.split("=");
          if (kv.length == 2) {
            String key = URLDecoder.decode(kv[0], "UTF-8");
            String value = URLDecoder.decode(kv[1], "UTF-8");
            if ("username".equals(key)) {
              username = value;
            } else if ("password".equals(key)) {
              password = value;
            }
          }
        }

        if (username != null && password != null && userStorage.register(username, password)) {
          LoginPage.sendLoginPage(socket);
        } else {
          RegisterFailurePage.sendRegisterFailurePage(socket, "Username already taken or invalid data.");
        }
      } else if (method.equals("GET") && (path.equals("/") || path.equals("/login"))) {
        LoginPage.sendLoginPage(socket);
      } else if (method.equals("GET") && path.equals("/register")) {
        RegisterPage.sendRegisterPage(socket);
      } else {
        NotFoundPage.sendNotFoundPage(socket);
      }
    } catch (IOException ioe) {
      System.out.println("Error: " + ioe);
    }
  }
}
