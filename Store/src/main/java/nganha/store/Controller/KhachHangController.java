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
import javafx.stage.StageStyle;
import nganha.store.BLL.KhachHangBLL;
import nganha.store.Model.KhachHang;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KhachHangController {
  @FXML
  private TableView<KhachHang> tblKhachHang;
  @FXML
  private TableColumn<KhachHang, String> colMaKH;
  @FXML
  private TableColumn<KhachHang, String> colTenKH;
  @FXML
  private TableColumn<KhachHang, String> colPhone;
  @FXML
  private TableColumn<KhachHang, String> colAddress;
  @FXML
  private TableColumn<KhachHang, String> colEmail;

  @FXML
  private TextField txtFind;
  private String searchKeyword = "";
  private ObservableList<KhachHang> khachHangList = FXCollections.observableArrayList();
  private ObservableList<KhachHang> filteredList = FXCollections.observableArrayList();

  private final KhachHangBLL khachHangBLL = new KhachHangBLL();

  @FXML
  public void initialize() {
    // Cài đặt các cellValueFactory cho mỗi cột trong bảng
    colMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
    colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
    colPhone.setCellValueFactory(new PropertyValueFactory<>("SDT"));
    colAddress.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    // Load dữ liệu khách hàng
    try {
      loadAllKhachHang();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    // Lắng nghe sự thay đổi trong TextField tìm kiếm
    txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
      searchKeyword = newValue.toLowerCase().trim();
      applyFilters(); // Gọi hàm áp dụng bộ lọc
    });
  }
  private void loadAllKhachHang() throws SQLException, ClassNotFoundException {
    // Lấy toàn bộ danh sách khách hàng từ BLL
    List<KhachHang> khachHangData = khachHangBLL.getAllKhachHang();
    khachHangList.clear();
    filteredList.clear();

    // Kiểm tra và xử lý dữ liệu null hoặc trống
    for (KhachHang kh : khachHangData) {
      if (kh.getTenKH() == null || kh.getTenKH().trim().isEmpty()) {
        kh.setTenKH("Không xác định");
      }
    }

    khachHangList.addAll(khachHangData);
    filteredList.addAll(khachHangData);
    tblKhachHang.setItems(filteredList);
  }

  @FXML
  public void handleAddKhachHang() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/addKhachHang.fxml"));
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void applyFilters() {
    filteredList.clear();

    // Áp dụng bộ lọc theo từ khóa tìm kiếm
    List<KhachHang> result = khachHangList.stream()
        .filter(sp -> (sp.getTenKH() != null && sp.getTenKH().toLowerCase().contains(searchKeyword)) ||
            (String.valueOf(sp.getMaKH()).contains(searchKeyword))) // Convert int to String
        .collect(Collectors.toList());

    filteredList.addAll(result);
    tblKhachHang.setItems(filteredList);
  }


  @FXML
  public void handleEditKhachHang() {
    KhachHang selectedKhachHang = tblKhachHang.getSelectionModel().getSelectedItem();

    if (selectedKhachHang == null) {
      showAlert("Lỗi", "Vui lòng chọn khách hàng để chỉnh sửa!");
      return;
    }

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/editKhachHang.fxml"));
      Parent root = loader.load();

      // Lấy controller của form chỉnh sửa
      ActKhachHangController editController = loader.getController();
      editController.setKhachHang(selectedKhachHang);

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
  public void handleDeleteKhachHang() throws Exception {
    KhachHang selectedKhachHang = tblKhachHang.getSelectionModel().getSelectedItem();

    if (selectedKhachHang != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Xác nhận xoá");
      alert.setHeaderText("Bạn có chắc chắn muốn xoá khách hàng này?");
      alert.setContentText("Tên khách hàng : " + selectedKhachHang.getTenKH() + "\nMã khách hàng: " + selectedKhachHang.getMaKH());

      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent() && result.get() == ButtonType.OK) {
        boolean success = khachHangBLL.deleteKhachHang(selectedKhachHang.getMaKH());
        if (success) {
          tblKhachHang.getItems().remove(selectedKhachHang);
          showAlert("Thành công", "Xoá khách hàng thành công.");
        } else {
          showAlert("Lỗi", "Không thể xoá khách hàng. Vui lòng thử lại.");
        }
      }
    } else {
      showAlert("Cảnh báo", "Vui lòng chọn một khách hàng để xoá.");
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
