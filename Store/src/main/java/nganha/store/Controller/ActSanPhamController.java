package nganha.store.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import nganha.store.BLL.SanPhamBLL;
import nganha.store.Model.NhanVien;
import nganha.store.Model.SanPham;
import nganha.store.Utils.FormUtils;

public class ActSanPhamController {
  @FXML private Label tenSPLabel;
  @FXML private Label giaLabel;
  @FXML private Label soLuongLabel;
  @FXML private Label mauSacLabel;
  @FXML private Label moTaLabel;
  @FXML private Label sizeLabel;

  @FXML private TextField txtTenSP;
  @FXML private TextField txtGia;
  @FXML private TextField txtSoLuong;
  @FXML private TextField txtMauSac;
  @FXML private TextField txtMoTa;
  @FXML private ComboBox<String> cbSize;

  @FXML private Button btnAdd;
  @FXML private Button btnSave;
  @FXML private Button btnCancel;

  private SanPham currentSanPham; // Sản phẩm hiện tại
  private final SanPhamBLL sanPhamBLL;

  public ActSanPhamController() {
    sanPhamBLL = new SanPhamBLL();
  }

  // Truyền dữ liệu sản phẩm vào form
  public void setSanPham(SanPham sanPham) {
    this.currentSanPham = sanPham;
    txtTenSP.setText(sanPham.getTenSP());
    txtGia.setText(String.valueOf(sanPham.getGiaBan()));
    txtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
    txtMauSac.setText(sanPham.getMauSac());
    txtMoTa.setText(sanPham.getMoTa());
    cbSize.setValue(sanPham.getSize().name());
  }

  public void setSanPhamDetail(SanPham sanPham) {
    this.currentSanPham = sanPham;

    // Hiển thị thông tin chi tiết của sản phẩm
    tenSPLabel.setText(sanPham.getTenSP());
    giaLabel.setText(String.valueOf(sanPham.getGiaBan()));
    soLuongLabel.setText(String.valueOf(sanPham.getSoLuong()));
    mauSacLabel.setText(sanPham.getMauSac());
    moTaLabel.setText(sanPham.getMoTa());
    sizeLabel.setText(sanPham.getSize().name());
  }

  @FXML
  private void handleSave() {
    try {
      if (!validateInput()) return;
      // Cập nhật thông tin vào đối tượng sản phẩm hiện tại
      currentSanPham.setTenSP(txtTenSP.getText());
      currentSanPham.setGiaBan(Double.parseDouble(txtGia.getText()));
      currentSanPham.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
      currentSanPham.setMauSac(txtMauSac.getText());
      currentSanPham.setMoTa(txtMoTa.getText());
      currentSanPham.setSize(SanPham.Size.valueOf(cbSize.getValue().toUpperCase()));

      // Gửi thông tin cập nhật qua BLL để xử lý
      if (sanPhamBLL.updateSanPham(currentSanPham)) {
        showAlert("Thành công", "Thông tin sản phẩm đã được cập nhật!");
        FormUtils.closeForm(btnSave);
      } else {
        showAlert("Lỗi", "Không thể cập nhật thông tin sản phẩm.");
      }

    } catch (NumberFormatException e) {
      showAlert("Lỗi", "Giá và số lượng phải là số hợp lệ.");
    } catch (Exception e) {
      e.printStackTrace();
      showAlert("Lỗi", "Đã xảy ra lỗi khi lưu thông tin.");
    }
  }

  @FXML
  private void handleAddSanPham() {
    try {
      // Kiểm tra tính hợp lệ của các trường nhập liệu
      if (!validateInput()) return;

      // Lấy dữ liệu từ form
      String tenSP = txtTenSP.getText().trim();
      double giaSP;
      int soLuongSP;

      // Kiểm tra và chuyển đổi giá trị nhập vào cho trường "Giá"
      try {
        giaSP = Double.parseDouble(txtGia.getText().trim());
      } catch (NumberFormatException e) {
        showAlert("Lỗi", "Giá sản phẩm không hợp lệ.");
        return;
      }

      // Kiểm tra và chuyển đổi giá trị nhập vào cho trường "Số lượng"
      try {
        soLuongSP = Integer.parseInt(txtSoLuong.getText().trim());
      } catch (NumberFormatException e) {
        showAlert("Lỗi", "Số lượng sản phẩm không hợp lệ.");
        return;
      }

      String mauSac = txtMauSac.getText().trim();
      String moTa = txtMoTa.getText().trim();
      String size = cbSize.getValue();

      // Kiểm tra xem size có hợp lệ không
      if (size == null || size.isEmpty()) {
        showAlert("Lỗi", "Vui lòng chọn kích thước.");
        return;
      }

      // Chuyển đổi size thành enum
      SanPham.Size sizeEnum;
      try {
        sizeEnum = SanPham.Size.valueOf(size.toUpperCase());
      } catch (IllegalArgumentException e) {
        showAlert("Lỗi", "Kích thước không hợp lệ.");
        return;
      }

      // Tạo đối tượng sản phẩm mới
      SanPham sanPham = new SanPham(0, tenSP, giaSP, soLuongSP, sizeEnum, mauSac, moTa);

      // Thêm sản phẩm qua BLL
      try {
        sanPhamBLL.addSanPham(sanPham);
        showAlert("Thành công", "Thêm sản phẩm thành công.");
        // Xóa các trường nhập liệu sau khi thêm thành công
        handleHuy();
      } catch (Exception e) {
        showAlert("Lỗi", "Đã có lỗi xảy ra khi thêm sản phẩm: " + e.getMessage());
      }
    } catch (Exception e) {
      showAlert("Lỗi", "Đã có lỗi xảy ra: " + e.getMessage());
    }
  }

  @FXML
  private void handleHuy() {
    FormUtils.closeForm(btnCancel);
  }

  private boolean validateInput() {
    String tenSP = txtTenSP.getText();
    String gia = txtGia.getText();
    String soLuong = txtSoLuong.getText();

    if (tenSP.isEmpty() || gia.isEmpty() || soLuong.isEmpty()) {
      showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
      return false;
    }

    try {
      Double.parseDouble(gia);
      Integer.parseInt(soLuong);
    } catch (NumberFormatException e) {
      showAlert("Lỗi", "Giá và số lượng phải là số hợp lệ.");
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
