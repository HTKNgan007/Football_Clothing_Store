package nganha.store.DAL;

import nganha.store.Model.KhachHang;
import nganha.store.Utils.DSUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAL {
  public List<KhachHang> getAllKhachHang() throws SQLException, ClassNotFoundException {
    List<KhachHang> khachHangList = new ArrayList<>();
    Connection connection = DSUtils.DBConnect();

    String query = "SELECT maKH, tenKH, sdt, diaChi, email FROM khachhang";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      int maKH = resultSet.getInt("maKH");
      String tenKH = resultSet.getString("tenKH");
      String sdt = resultSet.getString("sdt");
      String diaChi = resultSet.getString("diaChi");
      String email = resultSet.getString("email");

      KhachHang khachHang = new KhachHang(maKH, tenKH,sdt, diaChi, email);
      khachHangList.add(khachHang);
    }

    DSUtils.CloseConnect(connection);

    return khachHangList;
  }

  public void addKhachHang(KhachHang khachHang) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO khachhang (maKH, tenKH, sdt, diaChi, email) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, khachHang.getMaKH());
      stmt.setString(2, khachHang.getTenKH());
      stmt.setString(3, khachHang.getSDT());
      stmt.setString(4, khachHang.getDiaChi());
      stmt.setString(5, khachHang.getEmail());

      stmt.executeUpdate();
      DSUtils.CloseConnect(conn);
    }
  }

  public boolean updateKhachHang(KhachHang khachHang) {
    String query = "UPDATE sanpham SET maKH = ?, tenKH = ?, sdt = ?, diaChi = ?, email = ? WHERE maKH = ?";

    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, khachHang.getMaKH());
      ps.setString(2, khachHang.getTenKH());
      ps.setString(3, khachHang.getSDT());
      ps.setString(4, khachHang.getDiaChi());
      ps.setString(5, khachHang.getEmail());

      return ps.executeUpdate() > 0;
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean deleteKhachHang(int maKH) throws SQLException, ClassNotFoundException {
    String sql = "DELETE FROM khachhang WHERE maKH = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, maKH);
      return stmt.executeUpdate() > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }
}
