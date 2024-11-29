package nganha.store.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TrangChuController {
  @FXML
  private StackPane contentPane;

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
  private void handleTrangChu() {
    loadContent("TrangChu.fxml");
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
}
