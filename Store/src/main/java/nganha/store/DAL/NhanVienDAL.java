package nganha.store.DAL;

import nganha.store.Model.NhanVien;
import nganha.store.Model.Role;
import nganha.store.Utils.DSUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAL {

  public List<NhanVien> getAllNhanVien() throws SQLException, ClassNotFoundException {
    List<NhanVien> nhanVienList = new ArrayList<>();
    Connection connection = DSUtils.DBConnect();

    // Câu lệnh SQL để lấy dữ liệu nhân viên, chỉ lấy các cột cần thiết
    String query = "SELECT maNV, tenNV, SDT, username, role, email FROM nhanvien";  // Sửa tên bảng nếu cần
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    ResultSet resultSet = preparedStatement.executeQuery();

    // Duyệt qua các bản ghi trong kết quả truy vấn
    while (resultSet.next()) {
      int maNV = resultSet.getInt("maNV");
      String tenNV = resultSet.getString("tenNV");
      String SDT = resultSet.getString("SDT");
      String roleStr = resultSet.getString("role");
      NhanVien.Role role = NhanVien.Role.valueOf(roleStr.toUpperCase());  // Chuyển từ chuỗi sang enum
      String email = resultSet.getString("email");
      String username = resultSet.getString("username");

      // Tạo đối tượng NhanVien và thêm vào danh sách
      NhanVien nhanVien = new NhanVien(maNV, tenNV, SDT, username, "", role, email);
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
      stmt.setString(5, nhanVien.getSDT());
      stmt.setString(6, nhanVien.getEmail());
      stmt.setString(7, nhanVien.getRole().name());

      stmt.executeUpdate();
      DSUtils.CloseConnect(conn);
    }
  }

  public boolean updateNhanVien(NhanVien nhanVien) {
    StringBuilder query = new StringBuilder("UPDATE NhanVien SET tenNV = ?, SDT = ?, role = ?");

    if (nhanVien.getEmail() != null) {
      query.append(", email = ?");
    }
    if (nhanVien.getUsername() != null) {
      query.append(", username = ?");
    }
    // Kiểm tra password có cần update không
    if (nhanVien.getPassword() != null) {
      query.append(", pass = ?");
    }
    query.append(" WHERE maNV = ?");

    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement ps = conn.prepareStatement(query.toString())) {

      ps.setString(1, nhanVien.getTenNV());
      ps.setString(2, nhanVien.getSDT());
      ps.setString(3, nhanVien.getRole().name());

      int paramIndex = 4;
      if (nhanVien.getEmail() != null) {
        ps.setString(paramIndex++, nhanVien.getEmail());
      }
      if (nhanVien.getUsername() != null) {
        ps.setString(paramIndex++, nhanVien.getUsername());
      }
      if (nhanVien.getPassword() != null && !nhanVien.getPassword().isEmpty()) {
        ps.setString(paramIndex++, nhanVien.getPassword());
      }
      ps.setInt(paramIndex, nhanVien.getMaNV());

      return ps.executeUpdate() > 0;
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  public boolean deleteNhanVien(int maNV) throws SQLException, ClassNotFoundException {
    String sql = "DELETE FROM NhanVien WHERE maNV = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, maNV);
      return stmt.executeUpdate() > 0;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  public NhanVien Login(String username, String hashedPassword) throws Exception {
    String sql = "SELECT * FROM NhanVien WHERE Username = ? AND Pass = ?";
    try (Connection conn = DSUtils.DBConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, username);
      stmt.setString(2, hashedPassword);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          // Tạo đối tượng NhanVien từ dữ liệu trả về
          NhanVien nhanVien = new NhanVien();
          nhanVien.setMaNV(rs.getInt("MaNV"));
          nhanVien.setTenNV(rs.getString("TenNV"));
          nhanVien.setSDT(rs.getString("SDT"));
          nhanVien.setUsername(rs.getString("Username"));
          nhanVien.setPassword(rs.getString("Pass")); // Có thể bỏ qua việc set password nếu không cần
          nhanVien.setRole(Role.fromString(rs.getString("Role"))); // Sử dụng fromString
          nhanVien.setEmail(rs.getString("Email"));
          return nhanVien; // Trả về thông tin nhân viên
        }
      }
    } catch (Exception e) {
      throw new Exception("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
    return null; // Trả về null nếu không tìm thấy
  }
}
