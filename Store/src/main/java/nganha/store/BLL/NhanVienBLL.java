package nganha.store.BLL;

import nganha.store.DAL.NhanVienDAL;
import nganha.store.Model.NhanVien;
import nganha.store.Utils.ComonUtils;

import java.sql.SQLException;
import java.util.List;

public class NhanVienBLL {
  NhanVienDAL nhanVienDAL;

  // Constructor không tham số
  public NhanVienBLL() {
    this.nhanVienDAL = new NhanVienDAL();
  }

  public List<NhanVien> getAllNhanVien() throws SQLException, ClassNotFoundException {
    return nhanVienDAL.getAllNhanVien();
  }

  // Thêm nhân viên với xử lý nghiệp vụ
  public void addNhanVien(NhanVien nhanVien) throws Exception {
    // Kiểm tra tính hợp lệ
    if (nhanVien.getTenNV() == null || nhanVien.getTenNV().isEmpty()) {
      throw new Exception("Tên nhân viên không được để trống.");
    }
    if (nhanVien.getUsername() == null || nhanVien.getUsername().isEmpty()) {
      throw new Exception("Tên tài khoản không được để trống.");
    }
    if (nhanVien.getPassword() == null || nhanVien.getPassword().isEmpty()) {
      throw new Exception("Mật khẩu không được để trống.");
    }
    if (nhanVien.getRole() == null) {
      throw new Exception("Chức vụ không được để trống.");
    }

    // Băm mật khẩu trước khi gửi xuống DAL
    String hashedPassword = ComonUtils.hashPassword(nhanVien.getPassword());
    nhanVien.setPassword(hashedPassword);

    // Gửi xuống DAL để lưu vào CSDL
    nhanVienDAL.addNhanVien(nhanVien);
  }

  public boolean updateNhanVien(NhanVien nhanVien) {
    // Thực hiện kiểm tra logic nghiệp vụ nếu cần
    if (nhanVien == null || nhanVien.getMaNV() == 0) {
      System.out.println("Thông tin nhân viên không hợp lệ!");
      return false;
    }

    // Gửi yêu cầu cập nhật xuống DAL
    return nhanVienDAL.updateNhanVien(nhanVien);
  }

  public boolean checkLogin(String tenDN, String matKhau) throws Exception {
    // Kiểm tra dữ liệu đầu vào
    if (tenDN == null || tenDN.trim().isEmpty()) {
      throw new Exception("Tên đăng nhập không được để trống.");
    }
    if (matKhau == null || matKhau.trim().isEmpty()) {
      throw new Exception("Mật khẩu không được để trống.");
    }

    // Băm mật khẩu trước khi so sánh
    String hashedPassword = ComonUtils.hashPassword(matKhau);

    // Gọi DAL để kiểm tra
    return nhanVienDAL.Login(tenDN, hashedPassword);
  }

  public boolean deleteNhanVien(int maNV) {
    try {
      return nhanVienDAL.deleteNhanVien(maNV);  // Gọi phương thức trong DAL để xóa nhân viên
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return false;  // Nếu có lỗi trong quá trình xóa, trả về false
    }
  }
}
