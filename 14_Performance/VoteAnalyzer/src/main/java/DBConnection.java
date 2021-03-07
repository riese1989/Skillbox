import java.sql.*;

public class DBConnection {

  private static Connection connection;

  private static String dbName = "learn";
  private static String dbUser = "root";
  private static String dbPass = "root127349";

  public static Connection getConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + dbName +
                "?user=" + dbUser + "&password=" + dbPass);
        connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
        connection.createStatement().execute("CREATE TABLE voter_count(" +
            "id INT NOT NULL AUTO_INCREMENT, " +
            "name TINYTEXT NOT NULL, " +
            "birthDate DATE NOT NULL, " +
            "`count` INT NOT NULL, " +
            "PRIMARY KEY(id))");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void addToDB(String sql) throws SQLException {
    DBConnection.getConnection().createStatement().execute(sql);
  }

  public static long countDuplicatedVoters() throws SQLException {
    String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
    ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
    long count = 0;
    while (rs.next()) {
      ++count;
    }
    return count;
  }
}
