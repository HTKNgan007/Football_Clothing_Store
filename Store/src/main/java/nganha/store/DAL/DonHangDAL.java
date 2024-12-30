package nganha.store.DAL;
import nganha.store.Model.DonHang;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DonHangDAL {
  public List<DonHang> getAllDonHang() throws SQLException, ClassNotFoundException {
    List<DonHang> donHangList = new ArrayList<>();
    String query = """
            SELECT dh.MaDH, kh.TenKH, nv.TenNV, dh.NgayTao, dh.TongTien
            FROM DonHang dh
            JOIN KhachHang kh ON dh.MaKH = kh.MaKH
            JOIN NhanVien nv ON dh.MaNV = nv.MaNV
        """;

    Connection connection = DSUtils.DBConnect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
      DonHang donHang = new DonHang(
          resultSet.getInt("MaDH"),
          resultSet.getString("TenKH"),
          resultSet.getString("TenNV"),
          resultSet.getTimestamp("NgayTao"),
          resultSet.getDouble("TongTien")
      );
      donHangList.add(donHang);
    }

    DSUtils.CloseConnect(connection);
    return donHangList;
  }
}
