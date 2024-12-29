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
import nganha.store.BLL.KhachHangBLL;
import nganha.store.Model.KhachHang;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
  TextField txtFind;

  private final KhachHangBLL khachHangBLL = new KhachHangBLL();

  @FXML
  public void initialize() {
    // Cài đặt các cellValueFactory cho mỗi cột trong bảng
    colMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
    colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
    colPhone.setCellValueFactory(new PropertyValueFactory<>("SDT"));
    colAddress.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    // Lấy danh sách khách hàng từ BLL và hiển thị trên TableView
    try {
      loadKhachHangData();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  private void loadKhachHangData() throws SQLException, ClassNotFoundException {
    List<KhachHang> khachHangList = khachHangBLL.getAllKhachHang();
    if (khachHangList != null) {
      ObservableList<KhachHang> khachHangObservableList = FXCollections.observableArrayList(khachHangList);
      tblKhachHang.setItems(khachHangObservableList);
    } else {
      System.out.println("Không thể lấy dữ liệu khách hàng!");
    }
  }

  @FXML
  public void handleAddKhachHang() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/addKhachHang.fxml"));
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setTitle("Thêm Khách Hàng");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
      stage.setTitle("Sửa Sản Phẩm");
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

  @FXML
  public void handleSearchKhachHang() {
    String keyword = txtFind.getText().trim().toLowerCase();

    try {
      List<KhachHang> khachHangList = khachHangBLL.getAllKhachHang();

      if (!keyword.isEmpty()) {
        khachHangList = khachHangList.stream()
            .filter(sp -> sp.getTenKH().toLowerCase().contains(keyword))
            .toList();
      }

      ObservableList<KhachHang> khachHangObservableList = FXCollections.observableArrayList(khachHangList);
      tblKhachHang.setItems(khachHangObservableList);

      if (khachHangList.isEmpty()) {
        showAlert("Thông báo", "Không tìm thấy khách hàng nào phù hợp.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      showAlert("Lỗi", "Có lỗi xảy ra khi tìm kiếm: " + e.getMessage());
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
