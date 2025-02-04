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

    // Nếu có trường password, băm trước khi cập nhật
    if (nhanVien.getPassword() != null && !nhanVien.getPassword().isEmpty()) {
      String hashedPassword = ComonUtils.hashPassword(nhanVien.getPassword());
      nhanVien.setPassword(hashedPassword);
    }

    // Gửi yêu cầu cập nhật xuống DAL
    return nhanVienDAL.updateNhanVien(nhanVien);
  }

  public boolean deleteNhanVien(int maNV) {
    try {
      return nhanVienDAL.deleteNhanVien(maNV);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public NhanVien checkLogin(String tenDN, String matKhau) throws Exception {
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
    NhanVien nhanVien = nhanVienDAL.Login(tenDN, hashedPassword);

    if (nhanVien == null) {
      throw new Exception("Tên đăng nhập hoặc mật khẩu không đúng.");
    }
    return nhanVien; // Trả về thông tin nhân viên
  }
}
