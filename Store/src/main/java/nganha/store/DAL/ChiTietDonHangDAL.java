package nganha.store.DAL;

import nganha.store.Model.ChiTietDonHang;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangDAL {
  public List<ChiTietDonHang> getChiTietDonHangByMaDH(int maDH) throws SQLException, ClassNotFoundException {
    List<ChiTietDonHang> chiTietList = new ArrayList<>();
    String query = """
            SELECT ctdh.MaCTDH, sp.TenSP, ctdh.SoLuong, ctdh.Gia, sp.Size, sp.MauSac
            FROM ChiTietDonHang ctdh
            JOIN SanPham sp ON ctdh.MaSP = sp.MaSP
            WHERE ctdh.MaDH = ?
        """;

    Connection connection = DSUtils.DBConnect();
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setInt(1, maDH);

    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      ChiTietDonHang chiTiet = new ChiTietDonHang(
          resultSet.getInt("MaCTDH"),
          resultSet.getString("TenSP"),
          resultSet.getInt("SoLuong"),
          resultSet.getDouble("Gia"),
          resultSet.getString("Size"),
          resultSet.getString("MauSac")
      );
      chiTietList.add(chiTiet);
    }

    DSUtils.CloseConnect(connection);
    return chiTietList;
  }

//  public void themChiTietDonHang(ChiTietDonHangCSDL chiTietDonHang) {
//    String sql = "INSERT INTO ChiTietDonHang (maDH, maSP, soLuong, gia) VALUES (?, ?, ?, ?)";
//    try (Connection conn = DSUtils.DBConnect();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//      ps.setInt(1, chiTietDonHang.getMaDH());
//      ps.setInt(2, chiTietDonHang.getMaSP());
//      ps.setInt(3, chiTietDonHang.getSoLuong());
//      ps.setDouble(4, chiTietDonHang.getGia());
//
//      ps.executeUpdate();
//    } catch (SQLException | ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//  }
public void themChiTietDonHang(ChiTietDonHang chiTietDonHangCSDL) {
  String sql = "INSERT INTO ChiTietDonHang (maDH, maSP, soLuong, gia) VALUES (?, ?, ?, ?)";
  try (Connection connection = DSUtils.DBConnect();
       PreparedStatement ps = connection.prepareStatement(sql)) {

    ps.setInt(1, chiTietDonHangCSDL.getMaDH());
    ps.setInt(2, chiTietDonHangCSDL.getMaSP());
    ps.setInt(3, chiTietDonHangCSDL.getSoLuong());
    ps.setDouble(4, chiTietDonHangCSDL.getGia());

    int affectedRows = ps.executeUpdate();
    if (affectedRows == 0) {
      System.out.println("Không thể thêm chi tiết đơn hàng.");
    }
  } catch (SQLException | ClassNotFoundException e) {
    e.printStackTrace();
  }
}
}
