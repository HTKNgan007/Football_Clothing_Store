package nganha.store.DAL;

import nganha.store.Model.NhanVien;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAL {

  public List<NhanVien> getAllNhanVien() throws SQLException, ClassNotFoundException {
    List<NhanVien> nhanVienList = new ArrayList<>();
    Connection connection = DSUtils.DBConnect();

    // Câu lệnh SQL để lấy dữ liệu nhân viên, chỉ lấy các cột cần thiết
    String query = "SELECT maNV, tenNV, SDT, role, email FROM nhanvien";  // Sửa tên bảng nếu cần
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();

    // Duyệt qua các bản ghi trong kết quả truy vấn
    while (resultSet.next()) {
      int maNV = resultSet.getInt("maNV");
      String tenNV = resultSet.getString("tenNV");
      int SDT = resultSet.getInt("SDT");
      String roleStr = resultSet.getString("role");
      NhanVien.Role role = NhanVien.Role.valueOf(roleStr.toUpperCase());  // Chuyển từ chuỗi sang enum
      String email = resultSet.getString("email");

      // Tạo đối tượng NhanVien và thêm vào danh sách
      NhanVien nhanVien = new NhanVien(maNV, tenNV, SDT, "", "", role, email);
      nhanVienList.add(nhanVien);
    }

    // Đóng kết nối
    DSUtils.CloseConnect(connection);

    return nhanVienList;
  }

  public void addNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO NhanVien (maNV, tenNV, Username, Pass, SDT, email, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, nhanVien.getMaNV());
      stmt.setString(2, nhanVien.getTenNV());
      stmt.setString(3, nhanVien.getUsername());
      stmt.setString(4, nhanVien.getPassword()); // Password đã được băm ở BLL
      stmt.setInt(5, nhanVien.getSDT());
      stmt.setString(6, nhanVien.getEmail()); // Thêm email
      stmt.setString(7, nhanVien.getRole().name()); // Thêm role (chức vụ)

      stmt.executeUpdate();
      DSUtils.CloseConnect(conn);
    }

  }
  public boolean Delete(int id){
    return true;
  }

  public boolean Login(String username, String hashedPassword) throws Exception {
    String sql = "SELECT COUNT(*) FROM NhanVien WHERE Username = ? AND Pass = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, username);
      stmt.setString(2, hashedPassword);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0; // Trả về true nếu có ít nhất một bản ghi
        }
      }
    } catch (Exception e) {
      throw new Exception("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
    return false;
  }
}
