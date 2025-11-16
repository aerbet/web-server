import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HTMLResponse {
    
    private HTMLResponse() {
    }
    
    public static void sendHtmlResponse(Socket clientSocket, String htmlContent) throws IOException {
    byte[] data = htmlContent.getBytes("UTF-8");
    int fileLength = data.length;

    OutputStream out = clientSocket.getOutputStream();
    PrintWriter printWriter = new PrintWriter(out, true);
    printWriter.println("HTTP/1.1 200 OK");
    printWriter.println("Server: Java HTTP Server from Intern Labs 7.0 - JBD");
    printWriter.println("Content-type: text/html; charset=UTF-8");
    printWriter.println("Content-length: " + fileLength);
    printWriter.println("Connection: close");
    printWriter.println();
    printWriter.flush();

    BufferedOutputStream dataOut = new BufferedOutputStream(out);
    dataOut.write(data, 0, fileLength);
    dataOut.flush();
  }
}
