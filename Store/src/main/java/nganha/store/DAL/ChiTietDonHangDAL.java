package nganha.store.DAL;

import nganha.store.Model.ChiTietDonHang;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangDAL {
  public List<ChiTietDonHang> getChiTietDonHangByMaDH(int maDH) throws SQLException, ClassNotFoundException {
    List<ChiTietDonHang> chiTietList = new ArrayList<>();
    String query = "SELECT * FROM ChiTietDonHang WHERE MaDH = ?";

    Connection connection = DSUtils.DBConnect();
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, maDH);

    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      ChiTietDonHang chiTiet = new ChiTietDonHang(
          resultSet.getInt("MaCTDH"),
          resultSet.getInt("MaDH"),
          resultSet.getInt("MaSP"),
          resultSet.getInt("SoLuong"),
          resultSet.getDouble("Gia")
      );
      chiTietList.add(chiTiet);
    }

    DSUtils.CloseConnect(connection);
    return chiTietList;
  }
}
