import java.sql.Connection;
import java.sql.SQLException;

public class WriterDB implements Runnable {

  private String sql;
  private static Connection connection = DBConnection.getConnection();;

  public WriterDB(String sql) {
    this.sql = sql;
  }

  @Override
  public void run() {
    try {
      addToDB(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void addToDB(String sql) throws SQLException {
    connection.createStatement().execute(sql);
  }
}
