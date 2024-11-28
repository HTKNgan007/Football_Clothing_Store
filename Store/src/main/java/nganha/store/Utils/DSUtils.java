package nganha.store.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSUtils {
  static public Connection DBConnect() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    String strConn = "jdbc:mysql://localhost:3306/banquanao";
    Connection conn = DriverManager.getConnection(strConn, "root", "");
    return conn;
  }
  static public void CloseConnect(Connection connection) throws SQLException {
    connection.close();
  }
}
