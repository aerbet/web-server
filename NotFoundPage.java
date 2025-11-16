import java.net.Socket;
import java.io.IOException;

public class NotFoundPage {
  private NotFoundPage() {
  }

  public static void sendNotFoundPage(Socket socket) throws IOException {
    String message = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Not Found</title>
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
            .not-found-container {
              background-color: #fff;
              padding: 40px;
              border-radius: 8px;
              box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
              text-align: center;
            }
            h1 {
              color: #333;
            }
          </style>
        </head>
        <body>
          <div class="not-found-container">
            <h1>404 Not Found</h2>
          </div>
        </body>
        </html>
        """;
    HTMLResponse.sendHtmlResponse(socket, message);
  }
}
