package nganha.store.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import nganha.store.BLL.NhanVienBLL;
import nganha.store.BLL.SanPhamBLL;
import nganha.store.Model.KhachHang;
import nganha.store.Model.NhanVien;
import nganha.store.Model.SanPham;
import nganha.store.Utils.FormUtils;

public class ActKhachHangController {

  @FXML private TextField txtTenKH;
  @FXML private TextField txtSDT;
  @FXML private TextField txtDiaChi;
  @FXML private TextField txtEmail;

  @FXML private Button btnAdd;
  @FXML private Button btnSave;
  @FXML private Button btnCancel;

  private NhanVien currentNhanVien; // Nhân viên đang sửa
  private final NhanVienBLL nhanVienBLL;
  public ActNhanVienController() {
    nhanVienBLL = new NhanVienBLL();
  }

  // Truyền dữ liệu nhân viên vào form
  public void setNhanVien(NhanVien nhanVien) {
    this.currentNhanVien = nhanVien;
    txtTenNV.setText(nhanVien.getTenNV());
    txtSDT.setText(nhanVien.getSDT());
    txtEmail.setText(nhanVien.getEmail());
    cbRole.setValue(nhanVien.getRole().name());
    txtTKNV.setText(nhanVien.getUsername());
    txtMK.setText(""); // Không hiển thị mật khẩu
  }

  public void setNhanVienDetail(NhanVien nhanVien) {
    this.currentNhanVien = nhanVien;

    // Hiển thị thông tin chi tiết của nhân viên
    maNVLabel.setText(String.valueOf(nhanVien.getMaNV()));
    tenNVLabel.setText(nhanVien.getTenNV());
    sdtLabel.setText(nhanVien.getSDT());
    emailLabel.setText(nhanVien.getEmail());
    roleLabel.setText(nhanVien.getRole().name());
    usernameLabel.setText(nhanVien.getUsername());
  }

  @FXML
  private void handleSave() {
    try {
      if (!validateInput()) return;

      // Cập nhật thông tin vào đối tượng nhân viên hiện tại
      currentNhanVien.setTenNV(txtTenNV.getText());
      currentNhanVien.setSDT(txtSDT.getText());

      // Kiểm tra email
      String email = txtEmail.getText();
      if (email.isEmpty()) {
        currentNhanVien.setEmail(null); // Nếu không nhập email, để giá trị là null
      } else {
        currentNhanVien.setEmail(email);
      }

      // Cập nhật vai trò
      currentNhanVien.setRole(NhanVien.Role.valueOf(cbRole.getValue().toUpperCase()));

      // Nếu tài khoản không rỗng, cập nhật tài khoản
      String username = txtTKNV.getText();
      if (!username.isEmpty()) {
        currentNhanVien.setUsername(username);
      }

      // Nếu mật khẩu không rỗng, cập nhật mật khẩu
      String matKhau = txtMK.getText();
      if (matKhau.isEmpty()) {
        currentNhanVien.setPassword(null); // Nếu không nhập, để giá trị null
      } else {
        currentNhanVien.setPassword(matKhau); // Gán mật khẩu mới
      }

      // Gửi thông tin cập nhật qua BLL để xử lý
      if (nhanVienBLL.updateNhanVien(currentNhanVien)) {
        showAlert("Thành công", "Thông tin nhân viên đã được cập nhật!");
        FormUtils.closeForm(btnSave);
      } else {
        showAlert("Lỗi", "Không thể cập nhật thông tin nhân viên.");
      }

    } catch (NumberFormatException e) {
      showAlert("Lỗi", "Số điện thoại phải là số hợp lệ.");
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Lỗi", "Đã xảy ra lỗi khi lưu thông tin.");
    }
  }

  @FXML
  private void handleAddNhanVien() {
    String tenNV = txtTenNV.getText();
    String sdt = txtSDT.getText();
    String email = txtEmail.getText();
    String role = cbRole.getValue();
    String taiKhoan = txtTKNV.getText();
    String matKhau = txtMK.getText();

    // Kiểm tra nếu các trường không trống
    if (tenNV.isEmpty() || sdt.isEmpty() || role == null || taiKhoan.isEmpty() || matKhau.isEmpty()) {
      showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
      return;
    }

    // Chuyển role từ chuỗi thành Enum
    NhanVien.Role roleEnum = NhanVien.Role.valueOf(role.toUpperCase());

    // Tạo đối tượng NhanVien mới và gọi BLL để thêm nhân viên
    NhanVien nhanVien = new NhanVien(0, tenNV, sdt, taiKhoan, matKhau, roleEnum, email);

    try {
      nhanVienBLL.addNhanVien(nhanVien);
      showAlert("Thành công", "Thêm nhân viên thành công.");
      FormUtils.closeForm(btnAdd);
    } catch (Exception e) {
      showAlert("Lỗi", e.getMessage());
    }
  }

  @FXML
  private void handleHuy() {
    FormUtils.closeForm(btnCancel);
  }

  private boolean validateInput() {
    String tenNV = txtTenNV.getText();
    String sdt = txtSDT.getText();
    String email = txtEmail.getText();
    String role = cbRole.getValue();
    String taiKhoan = txtTKNV.getText();
    String matKhau = txtMK.getText();

    if (tenNV.isEmpty() || sdt.isEmpty() || role == null) {
      showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
      return false;
    }

    if (!sdt.matches("\\d+")) {
      showAlert("Lỗi", "Số điện thoại phải là số.");
      return false;
    }

    if (!email.isEmpty() && !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
      showAlert("Lỗi", "Email không hợp lệ.");
      return false;
    }

    return true;
  }

  // Hàm hiển thị thông báo
  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
