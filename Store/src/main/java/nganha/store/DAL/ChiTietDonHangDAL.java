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
}
