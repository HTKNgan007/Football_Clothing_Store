package nganha.store.DAL;

import nganha.store.Model.SanPham;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {
  public static List<SanPham> getAllSanPham() throws SQLException, ClassNotFoundException {
    List<SanPham> sanPhamList = new ArrayList<>();
    String query = "SELECT * FROM sanpham";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        SanPham sp = new SanPham(
            rs.getString("maSP"),
            rs.getString("tenSP"),
            rs.getString("loaiSP"),
            rs.getDouble("giaBan"),
            rs.getInt("soLuong"),
            rs.getString("size"),
            rs.getString("mauSac"),
            rs.getString("moTa")
        );
        sanPhamList.add(sp);
      }
    }
    return sanPhamList;
  }

  public static void addSanPham(SanPham sp) throws SQLException, ClassNotFoundException {
    String query = "INSERT INTO sanpham(maSP, tenSP, loaiSP, giaBan, soLuong, size, mauSac, moTa) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, sp.getMaSP());
      stmt.setString(2, sp.getTenSP());
      stmt.setString(3, sp.getLoaiSP());
      stmt.setDouble(4, sp.getGiaBan());
      stmt.setInt(5, sp.getSoLuong());
      stmt.setString(6, sp.getSize());
      stmt.setString(7, sp.getMauSac());
      stmt.setString(8, sp.getMoTa());
      stmt.executeUpdate();
    }
  }

  public static void updateSanPham(SanPham sp) throws SQLException, ClassNotFoundException {
    String query = "UPDATE sanpham SET tenSP = ?, loaiSP = ?, giaBan = ?, soLuong = ?, size = ?, mauSac = ?, moTa = ? WHERE maSP = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, sp.getTenSP());
      stmt.setString(2, sp.getLoaiSP());
      stmt.setDouble(3, sp.getGiaBan());
      stmt.setInt(4, sp.getSoLuong());
      stmt.setString(5, sp.getSize());
      stmt.setString(6, sp.getMauSac());
      stmt.setString(7, sp.getMoTa());
      stmt.setString(8, sp.getMaSP());
      stmt.executeUpdate();
    }
  }

  public static void deleteSanPham(String maSP) throws SQLException, ClassNotFoundException {
    String query = "DELETE FROM sanpham WHERE maSP = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, maSP);
      stmt.executeUpdate();
    }
  }
}
