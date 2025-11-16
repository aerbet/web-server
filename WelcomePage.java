import java.io.IOException;
import java.net.Socket;

public class WelcomePage {

  private WelcomePage() {
  }

  public static void sendWelcomePage(Socket socket) throws IOException {
    String message = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Welcome page</title>
          <style>
            body {
              font-family: Arial, sans-serif;
              display: flex;
              justify-content: center;
              align-items: center;
              min-height: 100vh;
              background-color: #f4f4f4;
              margin: 0;
            }
            .welcome-container {
                background-color: #fff;
                padding: 40px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            h2 {
                color: #28a745;
                margin-bottom: 20px;
            }
            h3 {
                color: #333;
            }
            .retry-btn {
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                margin-top: 20px;
            }
          </style>
        </head>
        <body>
            <div class="welcome-container">
                <h2>Welcome!</h2>
                <h3>Hello admin</h3>
                <p>You have successfully logged in.</p>
                <a href="/" class="retry-btn">Go to login page</a>
            </div>
        </body>
        </html>
        """;
    HTMLResponse.sendHtmlResponse(socket, message);
  }
}
