package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nganha.store.BLL.ChiTietDonHangBLL;
import nganha.store.BLL.DonHangBLL;
import nganha.store.Model.ChiTietDonHang;
import nganha.store.Model.DonHang;
import nganha.store.Utils.DSUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLyDonHangController {

  @FXML
  private Button XoaDH;

  @FXML
  private TableView<DonHang> tableDonHang;
  @FXML
  private TableColumn<DonHang, Integer> colMaDH;
  @FXML
  private TableColumn<DonHang, String> colTenKH;
  @FXML
  private TableColumn<DonHang, String> colTenNV;
  @FXML
  private TableColumn<DonHang, java.sql.Timestamp> colNgayTao;
  @FXML
  private TableColumn<DonHang, Double> colTongTien;

  @FXML
  private TableView<ChiTietDonHang> tableChiTietDonHang;
  @FXML
  private TableColumn<ChiTietDonHang, String> colTenSP;
  @FXML
  private TableColumn<ChiTietDonHang, Integer> colSoLuong;
  @FXML
  private TableColumn<ChiTietDonHang, Double> colGia;
  @FXML
  private TableColumn<ChiTietDonHang, String> colSize;
  @FXML
  private TableColumn<ChiTietDonHang, String> colMau;


  private ObservableList<DonHang> donHangList = FXCollections.observableArrayList();
  // Danh sách tạm sau khi lọc
  private ObservableList<DonHang> filteredList = FXCollections.observableArrayList();
  private ObservableList<ChiTietDonHang> chiTietDonHangList = FXCollections.observableArrayList();

  private DonHangBLL donHangBLL = new DonHangBLL();
  private ChiTietDonHangBLL chiTietDonHangBLL = new ChiTietDonHangBLL();

  private LocalDate selectedDate = null; // Biến để lưu ngày được chọn
  private String searchKeyword = ""; // Biến để lưu từ khóa tìm kiếm

  @FXML
  private TextField txtFind;
  @FXML
  private Button btnFind;
  @FXML
  private DatePicker DPickerThongKe; // DatePicker để chọn ngày
  @FXML
  private Label lblTongHD; // Hiển thị tổng số hóa đơn
  @FXML
  private Label lblDoanhThu; // Hiển thị tổng doanh thu


  @FXML
  public void initialize() {
    // Cấu hình bảng DonHang
    colMaDH.setCellValueFactory(new PropertyValueFactory<>("maDH"));
    colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
    colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
    colNgayTao.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
    colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));

    // Cấu hình bảng ChiTietDonHang
    colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    colGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
    colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
    colMau.setCellValueFactory(new PropertyValueFactory<>("mauSac"));

    // Load dữ liệu
    loadAllDonHang();

    // Lắng nghe sự kiện chọn ngày
    DPickerThongKe.valueProperty().addListener((obs, oldDate, newDate) -> {
      selectedDate = newDate;
      applyFilters();
    });

    // Lắng nghe sự kiện tìm kiếm
    txtFind.textProperty().addListener((obs, oldText, newText) -> {
      searchKeyword = newText.toLowerCase().trim();
      applyFilters();
    });

    // Lắng nghe sự kiện chọn dòng trong bảng DonHang
    tableDonHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        try {
          chiTietDonHangList.clear();
          chiTietDonHangList.addAll(chiTietDonHangBLL.getChiTietDonHangByMaDH(newSelection.getMaDH()));
        } catch (Exception e) {
          e.printStackTrace();
        }
        tableChiTietDonHang.setItems(chiTietDonHangList);
      }
    });
  }

  @FXML
  private void loadAllDonHang() {
    // Xóa bộ lọc và load lại toàn bộ dữ liệu
    try {
      donHangList.clear();
      filteredList.clear();
      selectedDate = null;
      searchKeyword = "";

      List<DonHang> donHangData = donHangBLL.getAllDonHang();

      // Thay thế tên khách hàng null bằng giá trị mặc định
      for (DonHang donHang : donHangData) {
        if (donHang.getTenKH() == null || donHang.getTenKH().trim().isEmpty()) {
          donHang.setTenKH("Admin");
        }
      }

      donHangList.addAll(donHangData);
      filteredList.addAll(donHangData);

      tableDonHang.setItems(filteredList);
      lblTongHD.setText(String.valueOf(filteredList.size()));
      lblDoanhThu.setText(String.format("%.2f", filteredList.stream().mapToDouble(DonHang::getTongTien).sum()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void applyFilters() {
    filteredList.clear();
    double totalRevenue = 0.0;

    // Áp dụng lọc
    List<DonHang> result = donHangList.stream()
        .filter(donHang -> (selectedDate == null || donHang.getNgayTao().toLocalDateTime().toLocalDate().equals(selectedDate)) &&
            (searchKeyword.isEmpty() || donHang.getTenKH().toLowerCase().contains(searchKeyword) ||
                donHang.getTenNV().toLowerCase().contains(searchKeyword)))
        .collect(Collectors.toList());

    filteredList.addAll(result);
    totalRevenue = result.stream().mapToDouble(DonHang::getTongTien).sum();

    // Cập nhật bảng và các nhãn
    tableDonHang.setItems(filteredList);
    lblTongHD.setText(String.valueOf(filteredList.size()));
    lblDoanhThu.setText(String.format("%.2f", totalRevenue));
  }

  @FXML
  private void deleteDonHang() {
    // Lấy đơn hàng được chọn
    DonHang selectedDonHang = tableDonHang.getSelectionModel().getSelectedItem();

    if (selectedDonHang == null) {
      // Nếu chưa chọn đơn hàng nào, hiển thị thông báo
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Cảnh báo");
      alert.setHeaderText(null);
      alert.setContentText("Vui lòng chọn một đơn hàng để xóa!");
      alert.showAndWait();
    } else {
      // Hiển thị hộp thoại xác nhận xóa
      Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
      confirmAlert.setTitle("Xác nhận xóa");
      confirmAlert.setHeaderText(null);
      confirmAlert.setContentText("Bạn có chắc chắn muốn xóa đơn hàng này?");

      confirmAlert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          try (Connection conn = DSUtils.DBConnect();
               PreparedStatement stmt = conn.prepareStatement("DELETE FROM ChiTietDonHang WHERE MaDH = ?")) {

            // Xóa chi tiết đơn hàng trước
            stmt.setInt(1, selectedDonHang.getMaDH());
            stmt.executeUpdate();

            // Xóa đơn hàng
            try (PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM DonHang WHERE MaDH = ?")) {
              stmt2.setInt(1, selectedDonHang.getMaDH());
              stmt2.executeUpdate();
            }

            // Xóa khỏi danh sách hiển thị
            donHangList.remove(selectedDonHang);

            // Hiển thị thông báo thành công
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Thành công");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Đơn hàng đã được xóa thành công!");
            successAlert.showAndWait();
          } catch (Exception e) {
            e.printStackTrace();

            // Hiển thị thông báo lỗi
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Đã xảy ra lỗi khi xóa đơn hàng: " + e.getMessage());
            errorAlert.showAndWait();
          }
        }
      });
    }
  }

  @FXML
  private void reloadAllData() {
    loadAllDonHang(); // Gọi lại phương thức load toàn bộ dữ liệu
    DPickerThongKe.setValue(null); // Xóa ngày đã chọn trong DatePicker
    txtFind.clear(); // Xóa nội dung tìm kiếm
  }
}
