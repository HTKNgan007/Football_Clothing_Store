package nganha.store.Utils;

import javafx.scene.Node;
import javafx.stage.Stage;

public class FormUtils {
  // Phương thức đóng form
  public static void closeForm(Node node) {
    if (node != null) {
      // Lấy Stage từ Node bất kỳ
      Stage stage = (Stage) node.getScene().getWindow();
      if (stage != null) {
        stage.close(); // Đóng form
      }
    }
  }
}
