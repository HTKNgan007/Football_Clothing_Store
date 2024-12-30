package nganha.store.DAL;

import nganha.store.Model.SanPham;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {
  public List<SanPham> getAllSanPham() throws SQLException, ClassNotFoundException {
    List<SanPham> sanPhamList = new ArrayList<>();
    Connection connection = DSUtils.DBConnect();

    String query = "SELECT maSP, tenSP, giaBan, soLuong, size, mauSac, moTa FROM sanpham";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      int maSP = resultSet.getInt("maSP");
      String tenSP = resultSet.getString("tenSP");
      double giaBan = resultSet.getDouble("giaBan");
      int soLuong = resultSet.getInt("soLuong");
      String sizeStr = resultSet.getString("size");
      SanPham.Size size = SanPham.Size.valueOf(sizeStr.toUpperCase());
      String mauSac = resultSet.getString("mauSac");
      String moTa = resultSet.getString("moTa");

      SanPham sanPham = new SanPham(maSP, tenSP, giaBan, soLuong, size, mauSac, moTa);
      sanPhamList.add(sanPham);
    }

    DSUtils.CloseConnect(connection);

    return sanPhamList;
  }

  public void addSanPham(SanPham sanPham) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO sanpham (maSP, tenSP, giaBan, soLuong, size, mauSac, moTa) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, sanPham.getMaSP());
      stmt.setString(2, sanPham.getTenSP());
      stmt.setDouble(3, sanPham.getGiaBan());
      stmt.setInt(4, sanPham.getSoLuong());
      stmt.setString(5, sanPham.getSize().name());
      stmt.setString(6, sanPham.getMauSac());
      stmt.setString(7, sanPham.getMoTa());

      stmt.executeUpdate();
      DSUtils.CloseConnect(conn);
    }
  }

  public boolean updateSanPham(SanPham sanPham) {
    String query = "UPDATE sanpham SET tenSP = ?, giaBan = ?, soLuong = ?, size = ?, mauSac = ?, moTa = ? WHERE maSP = ?";

    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, sanPham.getTenSP());
      ps.setDouble(2, sanPham.getGiaBan());
      ps.setInt(3, sanPham.getSoLuong());
      ps.setString(4, sanPham.getSize().name());
      ps.setString(5, sanPham.getMauSac());
      ps.setString(6, sanPham.getMoTa());
      ps.setInt(7, sanPham.getMaSP());

      return ps.executeUpdate() > 0;
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean deleteSanPham(int maSP) throws SQLException, ClassNotFoundException {
    String sql = "DELETE FROM sanpham WHERE maSP = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, maSP);
      return stmt.executeUpdate() > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Làm việc với đơn hàng - thêm vào đơn hàng
  public List<String> getTenSanPhamDistinct() throws SQLException, ClassNotFoundException {
    List<String> tenSanPhamList = new ArrayList<>();
    String query = "SELECT DISTINCT TenSP FROM SanPham";

    try (Connection conn = DSUtils.DBConnect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        tenSanPhamList.add(rs.getString("TenSP"));
      }
    }
    return tenSanPhamList;
  }

  public List<String> getMauSacByTenSP(String tenSP) throws SQLException, ClassNotFoundException {
    List<String> mauSacList = new ArrayList<>();
    String query = "SELECT DISTINCT MauSac FROM SanPham WHERE TenSP = ?";

    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, tenSP);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        mauSacList.add(rs.getString("MauSac"));
      }
    }
    return mauSacList;
  }

  public List<String> getSizeByTenSPAndMau(String tenSP, String mauSac) throws SQLException, ClassNotFoundException {
    List<String> sizeList = new ArrayList<>();
    String query = "SELECT DISTINCT Size FROM SanPham WHERE TenSP = ? AND MauSac = ?";

    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, tenSP);
      stmt.setString(2, mauSac);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        sizeList.add(rs.getString("Size"));
      }
    }
    return sizeList;
  }
}
