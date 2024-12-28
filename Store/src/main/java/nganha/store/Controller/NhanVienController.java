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
import java.util.Optional;

public class NhanVienController {

  @FXML
  private Button btnFind;

  @FXML
  private TextField txtFind;

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

  @FXML
  private void handleEditNhanVien() {
    NhanVien selectedNhanVien = tblEmployees.getSelectionModel().getSelectedItem();

    if (selectedNhanVien == null) {
      showAlert("Lỗi", "Vui lòng chọn một nhân viên để chỉnh sửa!");
      return;
    }

    try {
      // Tải file FXML của form chỉnh sửa
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/editNhanVien.fxml"));
      Parent root = loader.load();

      // Lấy controller của form chỉnh sửa
      ActNhanVienController editNhanVien= loader.getController();
      // Truyền nhân viên được chọn vào form chỉnh sửa
      editNhanVien.setNhanVien(selectedNhanVien);

      // Hiển thị form chỉnh sửa
      Stage stage = new Stage();
      stage.setTitle("Sửa Nhân Viên");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      showAlert("Lỗi", "Không thể mở form chỉnh sửa!");
    }
  }

  @FXML
  private void handleDeleteNhanVien() {
    NhanVien selectedNhanVien = tblEmployees.getSelectionModel().getSelectedItem();

    if (selectedNhanVien != null) {
      // Hiển thị hộp thoại xác nhận
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Xác nhận xoá");
      alert.setHeaderText("Bạn có chắc chắn muốn xoá nhân viên này?");
      alert.setContentText("Tên nhân viên: " + selectedNhanVien.getTenNV() + "\nMã nhân viên: " + selectedNhanVien.getMaNV());

      // Chờ người dùng chọn xác nhận hoặc huỷ
      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent() && result.get() == ButtonType.OK) {
        // Nếu người dùng nhấn "OK", tiến hành xoá
        boolean success = nhanVienBLL.deleteNhanVien(selectedNhanVien.getMaNV());
        if (success) {
          tblEmployees.getItems().remove(selectedNhanVien);
          showAlert("Thành công", "Xoá nhân viên thành công.");
        } else {
          showAlert("Lỗi", "Không thể xoá nhân viên. Vui lòng thử lại.");
        }
      } else {
        // Nếu người dùng nhấn "Cancel", không làm gì
        showAlert("Thông báo", "Nhân viên chưa bị xoá.");
      }
    } else {
      showAlert("Cảnh báo", "Vui lòng chọn một nhân viên để xoá.");
    }
  }

  @FXML
  private void handleViewDetail() {
    // Lấy nhân viên được chọn từ TableView
    NhanVien selectedNhanVien = tblEmployees.getSelectionModel().getSelectedItem();

    if (selectedNhanVien != null) {
      try {
        // Tải FXML của form chi tiết
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/detailNhanVien.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Chi tiết nhân viên");

        // Tạo scene và hiển thị cửa sổ
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        // Truyền đối tượng NhanVien vào form chi tiết
        ActNhanVienController controller = loader.getController();
        controller.setNhanVienDetail(selectedNhanVien);

        // Hiển thị cửa sổ chi tiết
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
        showAlert("Lỗi", "Không thể mở cửa sổ chi tiết.");
      }
    } else {
      showAlert("Cảnh báo", "Vui lòng chọn một nhân viên.");
    }
  }

  @FXML
  private void handleSearchNhanVien() {
    // Lấy giá trị từ ô tìm kiếm
    String keyword = txtFind.getText().trim().toLowerCase();

    try {
      // Lấy danh sách nhân viên từ BLL
      List<NhanVien> listNhanVien = nhanVienBLL.getAllNhanVien();

      // Lọc danh sách nếu có từ khóa tìm kiếm
      if (!keyword.isEmpty()) {
        listNhanVien = listNhanVien.stream()
            .filter(nv -> nv.getTenNV().toLowerCase().contains(keyword))
            .toList();
      }

      // Chuyển danh sách thành ObservableList và hiển thị trên bảng
      ObservableList<NhanVien> nhanVienObservableList = FXCollections.observableArrayList(listNhanVien);
      tblEmployees.setItems(nhanVienObservableList);

      // Thông báo nếu không tìm thấy nhân viên nào
      if (listNhanVien.isEmpty()) {
        showAlert("Thông báo", "Không tìm thấy nhân viên nào phù hợp.");
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      showAlert("Lỗi", "Có lỗi xảy ra khi tìm kiếm: " + e.getMessage());
    }
  }
  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
