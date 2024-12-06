package nganha.store.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class TrangChuController {

  @FXML
  private StackPane contentPane;

  @FXML
  public void initialize() {
    loadContent("QLDonHang.fxml");
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
    loadContent("TrangChu.fxml");
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
      // Đảm bảo đường dẫn tương đối chính xác
      String fullPath = "/nganha/store/" + fxmlFile;

      Node content = FXMLLoader.load(getClass().getResource(fullPath));
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
