import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserStorage {
  private static final String FILE_NAME = "users.csv";
  private final Map<String, String> users;

  public UserStorage() {
    this.users = new HashMap<>();
    loadUsers();
  }

  private void loadUsers() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
          users.put(parts[0].trim(), parts[1].trim());
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("User file not found, creating a new one: " + FILE_NAME);
    } catch (IOException e) {
      System.err.println("Error reading user file: " + e.getMessage());
    }
  }

  private void saveUser(String username, String hashedPassword) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
      writer.write(username + "," + hashedPassword + "\n");
    }
  }

  public boolean authenticate(String username, String password) {
    if (password == null || username == null || !users.containsKey(username)) {
      return false;
    }
    String inputHashedPassword = UserSecurity.hashPassword(password);
    String storedHashedPassword = users.get(username);
    return storedHashedPassword != null && storedHashedPassword.equals(inputHashedPassword);
  }

  public boolean register(String username, String password) throws IOException {
    if (users.containsKey(username) || username == null || password == null) {
      return false;
    }
    String hashedPassword = UserSecurity.hashPassword(password);
    users.put(username, hashedPassword);
    saveUser(username, hashedPassword);
    return true;
  }
}