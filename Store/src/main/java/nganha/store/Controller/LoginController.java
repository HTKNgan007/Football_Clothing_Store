package nganha.store.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nganha.store.BLL.NhanVienBLL;

import java.io.IOException;

public class LoginController {
  @FXML
  private TextField txtUsername;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private Label errorMessage;

  private final NhanVienBLL nhanVienBLL;

  public LoginController() {
    nhanVienBLL = new NhanVienBLL();
  }

  @FXML
  private void handleDangNhap(ActionEvent event) {
    String username = txtUsername.getText();
    String password = txtPassword.getText();

    try {
      boolean isLoggedIn = nhanVienBLL.checkLogin(username, password);
      if (isLoggedIn) {
        // Chuyển hướng sang giao diện chính
        loadFXML("/nganha/store/TrangChu.fxml", event);
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

  private void loadFXML(String fxmlPath, ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
    Parent root = fxmlLoader.load();

    // Lấy Stage hiện tại từ sự kiện
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Chuyển sang Scene mới
    stage.setScene(new Scene(root));
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }

}
