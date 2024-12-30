package nganha.store.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nganha.store.Model.NhanVien;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class TrangChuController {

  @FXML
  private Label lblUsername;
  private NhanVien nhanVien;

  @FXML
  private StackPane contentPane;

  @FXML
  public void initialize() {
    loadContent("HoaDon.fxml");
  }

  // Phương thức để truyền thông tin nhân viên
  public void setNhanVien(NhanVien nhanVien) {
    this.nhanVien = nhanVien;

    // Cập nhật tên nhân viên vào Label
    if (nhanVien != null) {
      lblUsername.setText("Xin chào, " + nhanVien.getTenNV() + "!");
    }
  }

  @FXML
  private void handleDonHang() {
    loadContent("HoaDon.fxml");
  }
  @FXML
  private void handleQLSanPham() {
    loadContent("QLSanPham.fxml");
  }

  @FXML
  private void handleQLNhanVien() {
    loadContent("QLNhanVien.fxml");
  }

  @FXML
  private void handleQLDonHang() {
    loadContent("QLDonHang.fxml");
  }
  @FXML
  private void handleKhachHang() {
    loadContent("QLKhachHang.fxml");
  }

  @FXML
  private void handleDangXuat(ActionEvent event) throws IOException {
    // Hiển thị hộp thoại xác nhận
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Xác nhận thoát");
    alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");

    // Xử lý lựa chọn
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        loadFXML("/nganha/store/login.fxml", event);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void loadContent(String fxmlFile) {
    try {
      String fullPath = "/nganha/store/" + fxmlFile;

      FXMLLoader loader = new FXMLLoader(getClass().getResource(fullPath));
      Node content = loader.load();

      // Truyền thông tin nhân viên vào controller của FXML
      if (fxmlFile.equals("HoaDon.fxml")) {
        HoaDonController hoaDonController = loader.getController();
        hoaDonController.setNhanVien(nhanVien);  // Chuyển thông tin nhân viên vào
      }

      // Lặp lại tương tự cho các FXML khác nếu cần
      contentPane.getChildren().clear();
      contentPane.getChildren().add(content);
    } catch (IOException e) {
      System.err.println("Could not load FXML file: " + fxmlFile);
      e.printStackTrace();
    }
  }

  private void loadFXML(String fxmlPath, javafx.event.ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
    Parent root = fxmlLoader.load();

    // Lấy Stage hiện tại từ sự kiện
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Chuyển sang Scene mới
    stage.setScene(new Scene(root));
//    stage.initStyle(StageStyle.DECORATED);
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }
}
