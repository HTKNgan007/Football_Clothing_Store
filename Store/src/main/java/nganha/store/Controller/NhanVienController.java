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
import nganha.store.BLL.NhanVienBLL;
import nganha.store.Model.NhanVien;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NhanVienController {

  @FXML
  private Button btnFind;

  @FXML
  private TextField txtFind;

  @FXML
  private TableView<NhanVien> tblNhanVien;

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

  private String searchKeyword = "";
  private ObservableList<NhanVien> nhanVienList = FXCollections.observableArrayList();
  private ObservableList<NhanVien> filteredList = FXCollections.observableArrayList();
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

    // Load dữ liệu nhân viên
    try {
      loadAllNhanVien();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    // Lắng nghe sự thay đổi trong TextField tìm kiếm
    txtFind.textProperty().addListener((observable, oldValue, newValue) -> {
      searchKeyword = newValue.toLowerCase().trim();
      applyFilters(); // Gọi hàm áp dụng bộ lọc
    });
  }

  private void loadAllNhanVien() throws SQLException, ClassNotFoundException {
    // Lấy toàn bộ danh sách nhân viên từ BLL
    List<NhanVien> nhanVienData = nhanVienBLL.getAllNhanVien();
    nhanVienList.clear();
    filteredList.clear();

    // Kiểm tra và xử lý dữ liệu null hoặc trống
    for (NhanVien nv : nhanVienData) {
      if (nv.getTenNV() == null || nv.getTenNV().trim().isEmpty()) {
        nv.setTenNV("Không xác định");
      }
    }

    nhanVienList.addAll(nhanVienData);
    filteredList.addAll(nhanVienData);
    tblNhanVien.setItems(filteredList);
  }

  private void applyFilters() {
    filteredList.clear();

    // Áp dụng bộ lọc theo từ khóa tìm kiếm
    List<NhanVien> result = nhanVienList.stream()
        .filter(nv -> (nv.getTenNV() != null && nv.getTenNV().toLowerCase().contains(searchKeyword)) ||
            (String.valueOf(nv.getMaNV()).contains(searchKeyword)))
        .collect(Collectors.toList());

    filteredList.addAll(result);
    tblNhanVien.setItems(filteredList);
  }

  @FXML
  private void handleAddNhanVien() {
    try {
      // Đảm bảo đường dẫn FXML chính xác
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/addNhanVien.fxml"));
      Parent root = loader.load();

      // Tạo và hiển thị cửa sổ mới
      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleEditNhanVien() {
    NhanVien selectedNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();

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
      stage.initStyle(StageStyle.UNDECORATED);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      showAlert("Lỗi", "Không thể mở form chỉnh sửa!");
    }
  }

  @FXML
  private void handleDeleteNhanVien() {
    NhanVien selectedNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();

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
          tblNhanVien.getItems().remove(selectedNhanVien);
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
    NhanVien selectedNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();

    if (selectedNhanVien != null) {
      try {
        // Tải FXML của form chi tiết
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nganha/store/detailNhanVien.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));

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
  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
