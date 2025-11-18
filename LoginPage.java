import java.net.Socket;
import java.io.IOException;

public class LoginPage {

  private LoginPage() {
  }

  public static void sendLoginPage(Socket socket) throws IOException {
    String message = """
        <!DOCTYPE html>
          <html lang="en">
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Login Form</title>
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
                .login-container {
                  background-color: #fff;
                  padding: 20px 30px;
                  border-radius: 8px;
                  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                  width: 300px;
                }
                h2 {
                  text-align: center;
                  margin-bottom: 20px;
                  color: #333;
                }
                .form-group {
                  margin-bottom: 15px;
                }
                label {
                  display: block;
                  margin-bottom: 5px;
                  color: #555;
                }
                input[type="text"],
                input[type="password"] {
                  width: 100%;
                  padding: 10px;
                  border: 1px solid #ddd;
                  border-radius: 4px;
                  box-sizing: border-box;
                }
                button {
                  width: 100%;
                  padding: 10px;
                  background-color: #007bff;
                  color: white;
                  border: none;
                  border-radius: 4px;
                  cursor: pointer;
                  font-size: 16px;
                }
                button:hover {
                  background-color: #0056b3;
                }
                p {
                  text-align: center;
                }
            </style>
          </head>
          <body>
            <div class="login-container">
              <h2>Login</h2>
              <form action="/login" method="post">
                <div class="form-group">
                  <label for="username">Username:</label>
                  <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                  <label for="password">Password:</label>
                  <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Log In</button>
                <div class="form-group">
                  <p>You don't have account? Then <a href="/register">register</a></p>
                </div>
              </form>
            </div>
          </body>
          </html>
        """;
    HTMLResponse.sendHtmlResponse(socket, message);
  }
}
