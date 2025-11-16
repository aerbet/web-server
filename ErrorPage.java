import java.io.IOException;
import java.net.Socket;

public class ErrorPage {
  private ErrorPage() {
  }

  public static void sendErrorPage(Socket socket, String errorMessage) throws IOException {
    String message = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Error</title>
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
              .error-container {
                  background-color: #fff;
                  padding: 40px;
                  border-radius: 8px;
                  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                  text-align: center;
              }
              h2 {
                  color: #dc3545;
                  margin-bottom: 20px;
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
            <div class="error-container">
              <h2>Login Failed</h2>
              <p>%s</p>
              <a href="/" class="retry-btn">Try Again</a>
            </div>
          </body>
        </html>
        """.formatted(errorMessage);
    HTMLResponse.sendHtmlResponse(socket, message);
  }
}
