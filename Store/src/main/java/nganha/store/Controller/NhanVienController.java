package nganha.store.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nganha.store.BLL.NhanVienBLL;
import nganha.store.Model.NhanVien;

public class NhanVienController {
  @FXML
  private TextField txtMaNV;

  @FXML
  private TextField txtTenNV;

  @FXML
  private TextField txtSDT;

  @FXML
  private TextField txtUsername;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private Label errorMessage;

  private final NhanVienBLL nhanVienBLL;

  public NhanVienController() {
    nhanVienBLL = new NhanVienBLL();
  }
  public void initialize(){

  }

  @FXML
  private void handleSaveNhanVien() {
    try {
      // Lấy dữ liệu từ form
      int maNV = Integer.parseInt(txtMaNV.getText());
      String tenNV = txtTenNV.getText();
      int sdt = Integer.parseInt(txtSDT.getText());
      String username = txtUsername.getText();
      String password = txtPassword.getText();
      // Sử dụng constructor đầy đủ tham số
      NhanVien nhanVien = new NhanVien(maNV, tenNV, sdt, username, password);

      // Gửi tới BLL để xử lý và lưu vào DB
      nhanVienBLL.addNhanVien(nhanVien);

      // Thông báo thành công
      showAlert(Alert.AlertType.INFORMATION, "Thành công", "Lưu thông tin nhân viên thành công!");
    } catch (NumberFormatException e) {
      showAlert(Alert.AlertType.ERROR, "Lỗi", "Mã NV và SĐT phải là số!");
    } catch (Exception e) {
      showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @FXML
  private void handleLogin() {
    String username = txtUsername.getText();
    String password = txtPassword.getText();

    try {
      boolean isLoggedIn = nhanVienBLL.checkLogin(username, password);
      if (isLoggedIn) {
        // Chuyển hướng sang giao diện chính
        System.out.println("Đăng nhập thành công!");
      } else {
        errorMessage.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
        errorMessage.setVisible(true);
      }
    } catch (Exception e) {
      errorMessage.setText(e.getMessage());
      errorMessage.setVisible(true);
    }
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
