import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

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
                    char symbol = (char)unicode;
                    System.out.print(symbol);
                    if(in.available() == 0) {
                      break;
                    }
                }
            } catch(IOException ioe) {
                System.out.println("Error: " + ioe);
            }
        }

        // System.out.println("All data has been red from User Agent");




        try {
          String message = "<h1>Hello, world from Erbol Zhaparov</h1>";
          message = """
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
                        width: calc(100% - 20px); /* Adjust for padding */
                        padding: 10px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        box-sizing: border-box; /* Include padding in width calculation */
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
                </style>
            </head>
            <body>
                <div class="login-container">
                    <h2>Login</h2>
                    <form action="/login" method="post">
                        <div class="form-group">
                            <label for="username">Username or Email:</label>
                            <input type="text" id="username" name="username" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" required>
                        </div>
                        <button type="submit">Log In</button>
                    </form>
                </div>
            </body>
            </html>
          """;
          byte[] data = message.getBytes();
          int fileLength = (int)message.length();

          OutputStream out = clientSocket.getOutputStream();
          PrintWriter printWriter = new PrintWriter(out, true);
          printWriter.println("HTTP/1.1 200 OK");
          printWriter.println("Server: Java HTTP Server from Intern Labs 7.0 - JBD");
          printWriter.println("Content-type: " + "text/html");
          printWriter.println("Content-length: " + fileLength);
          printWriter.println();
          printWriter.flush();

          BufferedOutputStream dataOut = new BufferedOutputStream(out);
          dataOut.write(data, 0, fileLength);
          dataOut.flush();
        } catch (IOException ioe) {
          System.out.println("Error in Output Stream.");
        }
    }

    public void go() {
        thread.start();
    }
}
