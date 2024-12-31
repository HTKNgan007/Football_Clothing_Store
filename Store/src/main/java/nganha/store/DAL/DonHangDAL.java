package nganha.store.DAL;
import nganha.store.Model.DonHang;
import nganha.store.Model.KhachHang;
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
            LEFT JOIN KhachHang kh ON dh.MaKH = kh.MaKH
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

  // Thêm hóa đơn mới
//  public int themDonHang(DonHang donHang) {
//    String sql = "INSERT INTO DonHang (maKH, maNV, ngayTao, tongTien) VALUES (?, ?, ?, ?)";
//    try (Connection connection = DSUtils.DBConnect();
//         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//      ps.setInt(1, donHang.getMaKH());
//      ps.setInt(2, donHang.getMaNV());
//      ps.setTimestamp(3, donHang.getNgayTao());
//      ps.setDouble(4, donHang.getTongTien());
//
//      ps.executeUpdate();
//
//      // Lấy mã đơn hàng mới tạo
//      try (ResultSet rs = ps.getGeneratedKeys()) {
//        if (rs.next()) {
//          return rs.getInt(1); // Trả về maDH mới tạo
//        }
//      }
//    } catch (SQLException | ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//    return -1; // Trả về -1 nếu có lỗi
//  }
  public int themDonHang(DonHang donHang) {
    String sql = "INSERT INTO DonHang (maKH, maNV, ngayTao, tongTien) VALUES (?, ?, ?, ?)";
    try (Connection connection = DSUtils.DBConnect();
         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, donHang.getMaKH());
      ps.setInt(2, donHang.getMaNV());
      ps.setTimestamp(3, donHang.getNgayTao());
      ps.setDouble(4, donHang.getTongTien());

      int affectedRows = ps.executeUpdate();  // Kiểm tra số dòng bị ảnh hưởng
      if (affectedRows == 0) {
        return -1;  // Không có dòng nào bị ảnh hưởng, có thể có lỗi trong việc thực thi câu lệnh
      }

      // Lấy mã đơn hàng mới tạo
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getInt(1); // Trả về maDH mới tạo
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return -1; // Trả về -1 nếu có lỗi
  }

  public KhachHang timKhachHangTheoSDT(String sdt) {
    String sql = "SELECT * FROM KhachHang WHERE SDT = ?";
    try (Connection connection = DSUtils.DBConnect();
         PreparedStatement ps = connection.prepareStatement(sql)) {

      ps.setString(1, sdt);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          KhachHang khachHang = new KhachHang();
          khachHang.setMaKH(rs.getInt("maKH"));
          khachHang.setTenKH(rs.getString("tenKH"));
          khachHang.setSDT(rs.getString("SDT"));
          return khachHang;
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null; // Trả về null nếu không tìm thấy
  }
}
