package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nganha.store.BLL.SanPhamBLL;
import nganha.store.Model.SanPham;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SanPhamController {
  @FXML
  private TableView<SanPham> tblSanPham;
  @FXML
  private TableColumn<SanPham, String> colMaSP;
  @FXML
  private TableColumn<SanPham, String> colTenSP;
  @FXML
  private TableColumn<SanPham, Double> colGiaBan;
  @FXML
  private TableColumn<SanPham, Integer> colSoLuong;
  @FXML
  private TableColumn<SanPham, String> colSize;
  @FXML
  private TableColumn<SanPham, String> colMauSac;

  @FXML TextField txtFind;
  private String searchKeyword = "";
  private ObservableList<SanPham> sanPhamList = FXCollections.observableArrayList();
  private ObservableList<SanPham> filteredList = FXCollections.observableArrayList();

  private final SanPhamBLL sanPhamBLL = new SanPhamBLL();

  @FXML
  public void initialize() {
    // Cài đặt các cellValueFactory cho mỗi cột trong bảng
    colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
    colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
    colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
    colMauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));

    // Load dữ liệu sản phẩm
    try {
      loadAllSanPham();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    // Lắng nghe sự thay đổi trong TextField tìm kiếm
    txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
      searchKeyword = newValue.toLowerCase().trim();
      applyFilters(); // Gọi hàm áp dụng bộ lọc
    });
  }
  private void loadAllSanPham() throws SQLException, ClassNotFoundException {
    // Lấy toàn bộ danh sách sản phẩm từ BLL
    List<SanPham> sanPhamData = sanPhamBLL.getAllSanPham();
    sanPhamList.clear();
    filteredList.clear();

    // Kiểm tra và xử lý dữ liệu null hoặc trống
    for (SanPham sp : sanPhamData) {
      if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
        sp.setTenSP("Không xác định");
      }
    }

    sanPhamList.addAll(sanPhamData);
    filteredList.addAll(sanPhamData);
    tblSanPham.setItems(filteredList);
  }

  private void applyFilters() {
    filteredList.clear();

    // Áp dụng bộ lọc theo từ khóa tìm kiếm
    List<SanPham> result = sanPhamList.stream()
        .filter(sp -> (sp.getTenSP() != null && sp.getTenSP().toLowerCase().contains(searchKeyword)) ||
            (String.valueOf(sp.getMaSP()).contains(searchKeyword))) // Convert int to String
        .collect(Collectors.toList());

    filteredList.addAll(result);
    tblSanPham.setItems(filteredList);
  }

  @FXML
  public void handleAddSanPham() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/addSanPham.fxml"));
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleEditSanPham() {
    SanPham selectedSanPham = tblSanPham.getSelectionModel().getSelectedItem();

    if (selectedSanPham == null) {
      showAlert("Lỗi", "Vui lòng chọn một sản phẩm để chỉnh sửa!");
      return;
    }

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/editSanPham.fxml"));
      Parent root = loader.load();

      // Lấy controller của form chỉnh sửa
      ActSanPhamController editController = loader.getController();
      editController.setSanPham(selectedSanPham);

      // Hiển thị form chỉnh sửa
      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      showAlert("Lỗi", "Không thể mở form chỉnh sửa!");
    }
  }

  @FXML
  public void handleDeleteSanPham() throws Exception {
    SanPham selectedSanPham = tblSanPham.getSelectionModel().getSelectedItem();

    if (selectedSanPham != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Xác nhận xoá");
      alert.setHeaderText("Bạn có chắc chắn muốn xoá sản phẩm này?");
      alert.setContentText("Tên sản phẩm: " + selectedSanPham.getTenSP() + "\nMã sản phẩm: " + selectedSanPham.getMaSP());

      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent() && result.get() == ButtonType.OK) {
        boolean success = sanPhamBLL.deleteSanPham(selectedSanPham.getMaSP());
        if (success) {
          tblSanPham.getItems().remove(selectedSanPham);
          showAlert("Thành công", "Xoá sản phẩm thành công.");
        } else {
          showAlert("Lỗi", "Không thể xoá sản phẩm. Vui lòng thử lại.");
        }
      }
    } else {
      showAlert("Cảnh báo", "Vui lòng chọn một sản phẩm để xoá.");
    }
  }

  @FXML
  public void handleViewDetail() {
    SanPham selectedSanPham = tblSanPham.getSelectionModel().getSelectedItem();

    if (selectedSanPham != null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/detailSanPham.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(loader.load()));

        ActSanPhamController controller = loader.getController();
        controller.setSanPhamDetail(selectedSanPham);

        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
        showAlert("Lỗi", "Không thể mở cửa sổ chi tiết.");
      }
    } else {
      showAlert("Cảnh báo", "Vui lòng chọn một sản phẩm.");
    }
  }

  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
