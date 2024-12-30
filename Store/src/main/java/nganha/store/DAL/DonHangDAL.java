package nganha.store.DAL;
import nganha.store.Model.DonHang;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DonHangDAL {
  public List<DonHang> getAllDonHang() throws SQLException, ClassNotFoundException {
    List<DonHang> donHangList = new ArrayList<>();
    String query = "SELECT * FROM DonHang";

    Connection connection = DSUtils.DBConnect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
      DonHang donHang = new DonHang(
          resultSet.getInt("MaDH"),
          resultSet.getInt("MaKH"),
          resultSet.getInt("MaNV"),
          resultSet.getTimestamp("NgayTao"),
          resultSet.getDouble("TongTien")
      );
      donHangList.add(donHang);
    }

    DSUtils.CloseConnect(connection);
    return donHangList;
  }
}
