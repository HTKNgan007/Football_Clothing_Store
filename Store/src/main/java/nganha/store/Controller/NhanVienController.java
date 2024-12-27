package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import nganha.store.BLL.NhanVienBLL;
import nganha.store.Model.NhanVien;
import nganha.store.Utils.DSUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NhanVienController {
  @FXML
  private TableView<NhanVien> tblEmployees;

  @FXML
  private TableColumn<NhanVien, Integer> colMaNV;

  @FXML
  private TableColumn<NhanVien, String> colTenNV;

  @FXML
  private TableColumn<NhanVien, String> colRole;

  @FXML
  private TableColumn<NhanVien, Integer> colPhone;

  @FXML
  private TableColumn<NhanVien, String> colEmail;

  private final NhanVienBLL nhanVienBLL;

  public NhanVienController() {
    nhanVienBLL = new NhanVienBLL();
  }

  @FXML
  public void initialize() {
    // Gán các cột cho TableView
    colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
    colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
    colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    colPhone.setCellValueFactory(new PropertyValueFactory<>("SDT"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    // Lấy dữ liệu từ BLL và hiển thị lên bảng
    try {
      loadNhanVienData();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void loadNhanVienData() throws SQLException, ClassNotFoundException {
    // Lấy danh sách nhân viên từ BLL
    List<NhanVien> listNhanVien = nhanVienBLL.getAllNhanVien();

    // Kiểm tra danh sách không null và tạo ObservableList
    if (listNhanVien != null) {
      ObservableList<NhanVien> nhanVienObservableList = FXCollections.observableArrayList(listNhanVien);
      tblEmployees.setItems(nhanVienObservableList);
    } else {
      System.out.println("Không thể lấy dữ liệu nhân viên!");
    }
  }

  @FXML
  private void handleAddNhanVien() {
    try {
      // Đảm bảo đường dẫn FXML chính xác
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/addNhanVien.fxml"));
      Parent root = loader.load();

      // Tạo và hiển thị cửa sổ mới
      Stage stage = new Stage();
      stage.setTitle("Thêm Nhân Viên");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  @FXML
//  private void handleSaveNhanVien() {
//    try {
//      // Lấy dữ liệu từ form
//      int maNV = Integer.parseInt(txtMaNV.getText());
//      String tenNV = txtTenNV.getText();
//      int sdt = Integer.parseInt(txtSDT.getText());
//      String username = txtUsername.getText();
//      String password = txtPassword.getText();
//      // Sử dụng constructor đầy đủ tham số
//      NhanVien nhanVien = new NhanVien(maNV, tenNV, sdt, username, password);
//
//      // Gửi tới BLL để xử lý và lưu vào DB
//      nhanVienBLL.addNhanVien(nhanVien);
//
//      // Thông báo thành công
//      showAlert(Alert.AlertType.INFORMATION, "Thành công", "Lưu thông tin nhân viên thành công!");
//    } catch (NumberFormatException e) {
//      showAlert(Alert.AlertType.ERROR, "Lỗi", "Mã NV và SĐT phải là số!");
//    } catch (Exception e) {
//      showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra: " + e.getMessage());
//      e.printStackTrace();
//    }
//  }


//  @FXML
//  private void handleDangKy(ActionEvent event) {
//    try {
//      // Tải file FXML mới
//      loadFXML("/nganha/store/signup.fxml", event);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  private void showAlert(Alert.AlertType alertType, String title, String message) {
//    Alert alert = new Alert(alertType);
//    alert.setTitle(title);
//    alert.setHeaderText(null);
//    alert.setContentText(message);
//    alert.showAndWait();
//  }
}
