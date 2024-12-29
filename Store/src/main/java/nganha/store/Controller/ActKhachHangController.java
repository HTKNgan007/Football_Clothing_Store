package nganha.store.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import nganha.store.BLL.KhachHangBLL;
import nganha.store.Model.KhachHang;
import nganha.store.Utils.FormUtils;

public class ActKhachHangController {

  @FXML private TextField txtTenKH;
  @FXML private TextField txtSDT;
  @FXML private TextField txtDiaChi;
  @FXML private TextField txtEmail;

  @FXML private Button btnAdd;
  @FXML private Button btnSave;
  @FXML private Button btnCancel;

  private KhachHang currentKhachHang;
  private final KhachHangBLL khachHangBLL;
  public ActKhachHangController() {
    khachHangBLL = new KhachHangBLL();
  }

  // Truyền dữ liệu khách hàng vào form
  public void setKhachHang(KhachHang khachHang) {
    this.currentKhachHang = khachHang;
    txtTenKH.setText(khachHang.getTenKH());
    txtSDT.setText(khachHang.getSDT());
    txtEmail.setText(khachHang.getEmail());
    txtDiaChi.setText(khachHang.getDiaChi());
  }

  @FXML
  private void handleSave() {
    try {
      if (!validateInput()) return;

      // Cập nhật thông tin vào đối tượng hiện tại
      currentKhachHang.setTenKH(txtTenKH.getText());
      currentKhachHang.setSDT(txtSDT.getText());

      // Kiểm tra email
      String email = txtEmail.getText();
      if (email == null || email.isEmpty()) {
        currentKhachHang.setEmail(null); // Nếu không nhập email, để giá trị là null
      } else {
        currentKhachHang.setEmail(email);
      }
      String diaChi = txtDiaChi.getText();
      if (diaChi == null || diaChi.isEmpty()) {
        currentKhachHang.setDiaChi(null); // Nếu không nhập địa chỉ, để giá trị là null
      } else {
        currentKhachHang.setDiaChi(diaChi);
      }

      // Gửi thông tin cập nhật qua BLL để xử lý
      if (khachHangBLL.updateKhachHang(currentKhachHang)) {
        showAlert("Thành công", "Thông tin khách hàng đã được cập nhật!");
        FormUtils.closeForm(btnSave);
      } else {
        showAlert("Lỗi", "Không thể cập nhật thông tin khách hàng.");
      }

    } catch (NumberFormatException e) {
      showAlert("Lỗi", "Số điện thoại phải là số hợp lệ.");
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Lỗi", "Đã xảy ra lỗi khi lưu thông tin.");
    }
  }

  @FXML
  private void handleAddKhachHang() {
    String tenKH = txtTenKH.getText();
    String sdt = txtSDT.getText();
    String email = txtEmail.getText();
    String diaChi = txtDiaChi.getText();

    // Kiểm tra nếu các trường không trống
    if (tenKH.isEmpty() || sdt.isEmpty()) {
      showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin tên khách hàng và SDT");
      return;
    }

    // Tạo đối tượng KhachHang mới và gọi BLL để thêm nhân viên
    KhachHang khachHang = new KhachHang(0, tenKH, sdt, diaChi, email);
    try {
      khachHangBLL.addKhachHang(khachHang);
      showAlert("Thành công", "Thêm khách hàng thành công.");
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
    String tenKH = txtTenKH.getText();
    String sdt = txtSDT.getText();
    String email = txtEmail.getText();

    if (tenKH.isEmpty() || sdt.isEmpty()) {
      showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
      return false;
    }

    if (!sdt.matches("\\d+")) {
      showAlert("Lỗi", "Số điện thoại phải là số.");
      return false;
    }

    if (email != null && !email.isEmpty() && !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
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
