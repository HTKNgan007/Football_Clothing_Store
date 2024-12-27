package nganha.store.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nganha.store.BLL.NhanVienBLL;
import nganha.store.DAL.NhanVienDAL;
import nganha.store.Model.NhanVien;

public class AddNhanVienController {
  @FXML
  private TextField txtTenNV; // TextField cho Tên NV

  @FXML
  private TextField txtSDT; // TextField cho Số điện thoại

  @FXML
  private TextField txtEmail; // TextField cho Email

  @FXML
  private ComboBox<String> cbRole; // ComboBox cho Chức vụ

  @FXML
  private TextField txtTKNV; // TextField cho Tài khoản

  @FXML
  private TextField txtMK; // TextField cho Mật khẩu

  @FXML
  private Button btnAdd; // Button Thêm

  @FXML
  private Button btnCancel; // Button Huỷ

  private final NhanVienBLL nhanVienBLL;
  public AddNhanVienController() {
    nhanVienBLL = new NhanVienBLL();
  }

  @FXML
  private void handleAddEmployee() {
    String tenNV = txtTenNV.getText();
    String sdt = txtSDT.getText();
    String email = txtEmail.getText();
    String role = cbRole.getValue();
    String taiKhoan = txtTKNV.getText();
    String matKhau = txtMK.getText();

    // Kiểm tra nếu các trường không trống
    if (tenNV.isEmpty() || sdt.isEmpty() || email.isEmpty() || role == null || taiKhoan.isEmpty() || matKhau.isEmpty()) {
      showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
      return;
    }

    // Chuyển role từ chuỗi thành Enum
    NhanVien.Role roleEnum = NhanVien.Role.valueOf(role.toUpperCase());

    // Tạo đối tượng NhanVien mới và gọi BLL để thêm nhân viên
    NhanVien nhanVien = new NhanVien(0, tenNV, Integer.parseInt(sdt), taiKhoan, matKhau, roleEnum, email);

    try {
      nhanVienBLL.addNhanVien(nhanVien);
      showAlert("Thành công", "Thêm nhân viên thành công.");
      // Xóa các trường nhập liệu sau khi thêm thành công
      handleCancel();
    } catch (Exception e) {
      showAlert("Lỗi", e.getMessage());
    }
  }

  // Hàm xử lý khi nhấn nút "Huỷ"
  @FXML
  private void handleCancel() {
    // Xóa các trường nhập liệu
    txtTenNV.clear();
    txtSDT.clear();
    txtEmail.clear();
    cbRole.getSelectionModel().clearSelection();
    txtTKNV.clear();
    txtMK.clear();
    closeForm();
  }

  // Hàm hiển thị thông báo
  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  // Hàm đóng form và quay lại màn hình trước đó
  private void closeForm() {
    Stage stage = (Stage) btnAdd.getScene().getWindow();
    stage.close();
  }
}
