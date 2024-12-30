package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import nganha.store.BLL.DonHangBLL;
import nganha.store.BLL.SanPhamBLL;
import nganha.store.DAL.ChiTietDonHangDAL;
import nganha.store.DAL.DonHangDAL;
import nganha.store.Model.*;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

public class HoaDonController {
  @FXML
  private Label lblMaSP;
  @FXML
  private ComboBox<String> comboBoxTenSP;
  @FXML
  private ComboBox<String> comboBoxMau;
  @FXML
  private ComboBox<String> comboBoxSize;
  @FXML
  private TextField textFieldSoluong;
  @FXML
  private Button btnMinus;
  @FXML
  private Button btnPlus;
  @FXML
  private Label lblGiaSP;
  @FXML
  private TextField textFieldSDT;
  @FXML
  private Label lblMaKH;
  @FXML
  private Label lblTenKH;

  @FXML
  private TableView<ChiTietDonHang> tableViewCTHD;

  @FXML
  private TableColumn<ChiTietDonHang, String> colMaSP;

  @FXML
  private TableColumn<ChiTietDonHang, String> colTenSP;

  @FXML
  private TableColumn<ChiTietDonHang, String> colMauSac;

  @FXML
  private TableColumn<ChiTietDonHang, String> colSize;

  @FXML
  private TableColumn<ChiTietDonHang, Integer> colSoLuong;

  @FXML
  private TableColumn<ChiTietDonHang, Double> colGia;
  @FXML
  private Text textThanhTien;

  // Giá của sản phẩm khi thêm sp right
  private double currentPrice = 0;
  // ObservableList để lưu dữ liệu
  private ObservableList<ChiTietDonHang> cthdList = FXCollections.observableArrayList();
  private DonHangBLL donHangBLL = new DonHangBLL();

  private SanPhamBLL sanPhamBLL = new SanPhamBLL();

  private NhanVien currentNhanVien;  // Biến lưu thông tin nhân viên

  // Phương thức này dùng để truyền thông tin nhân viên vào controller
  public void setNhanVien(NhanVien nhanVien) {
    this.currentNhanVien = nhanVien;
  }

  @FXML
  public void initialize() {
    try {
      // Load tên sản phẩm
      List<String> tenSanPhamList = sanPhamBLL.getTenSanPhamDistinct();
      comboBoxTenSP.setItems(FXCollections.observableArrayList(tenSanPhamList));

      // Lắng nghe sự kiện chọn tên sản phẩm
      comboBoxTenSP.setOnAction(event -> {
        String selectedTenSP = comboBoxTenSP.getValue();
        if (selectedTenSP != null) {
          try {
            List<String> mauSacList = sanPhamBLL.getMauSacByTenSP(selectedTenSP);
            comboBoxMau.setItems(FXCollections.observableArrayList(mauSacList));
            updateMaSPLabel();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      // Lắng nghe sự kiện chọn màu
      comboBoxMau.setOnAction(event -> {
        String selectedTenSP = comboBoxTenSP.getValue();
        String selectedMau = comboBoxMau.getValue();
        if (selectedTenSP != null && selectedMau != null) {
          try {
            List<String> sizeList = sanPhamBLL.getSizeByTenSPAndMau(selectedTenSP, selectedMau);
            comboBoxSize.setItems(FXCollections.observableArrayList(sizeList));
            updateMaSPLabel();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      // Lắng nghe sự kiện chọn size
      comboBoxSize.setOnAction(event -> {
        String selectedTenSP = comboBoxTenSP.getValue();
        String selectedMau = comboBoxMau.getValue();
        String selectedSize = comboBoxSize.getValue();

        if (selectedTenSP != null && selectedMau != null && selectedSize != null) {
          try {
            // Lấy giá của sản phẩm từ CSDL
            currentPrice = sanPhamBLL.getGiaByTenSPMauSize(selectedTenSP, selectedMau, selectedSize);
            updateGiaSPLabel(); // Cập nhật nhãn giá sản phẩm
            updateMaSPLabel();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      // Sự kiện nút Minus (-)
      btnMinus.setOnAction(event -> {
        int currentQuantity = Integer.parseInt(textFieldSoluong.getText());
        if (currentQuantity > 1) { // Đảm bảo số lượng không nhỏ hơn 1
          textFieldSoluong.setText(String.valueOf(currentQuantity - 1));
          updateGiaSPLabel(); // Cập nhật giá sản phẩm
        }
      });

      // Sự kiện nút Plus (+)
      btnPlus.setOnAction(event -> {
        int currentQuantity = Integer.parseInt(textFieldSoluong.getText());
        textFieldSoluong.setText(String.valueOf(currentQuantity + 1));
        updateGiaSPLabel(); // Cập nhật giá sản phẩm
      });

      // Lắng nghe thay đổi trong textFieldSoluong
      textFieldSoluong.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) { // Chỉ cho phép số
          textFieldSoluong.setText(newValue.replaceAll("[^\\d]", ""));
        } else if (!newValue.isEmpty()) {
          updateGiaSPLabel(); // Cập nhật giá sản phẩm
        }
      });

      textFieldSDT.textProperty().addListener((observable, oldValue, newValue) -> handleSDTChange());

    } catch (Exception e) {
      e.printStackTrace();
    }

    colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
    colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    colMauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
    colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
    colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    colGia.setCellValueFactory(new PropertyValueFactory<>("gia"));

    tableViewCTHD.setItems(cthdList);
  }

  @FXML
  private void handleBtnThem() {
    try {
      int maSP = Integer.parseInt(lblMaSP.getText());
      String tenSP = comboBoxTenSP.getValue();
      String mauSac = comboBoxMau.getValue();
      String size = comboBoxSize.getValue();
      int soLuong = Integer.parseInt(textFieldSoluong.getText());
      double donGia = currentPrice; // Giá hiện tại của sản phẩm
      double thanhTien = donGia * soLuong;

      // Tạo đối tượng ChiTietDonHang mới
      ChiTietDonHang chiTietDonHang = new ChiTietDonHang(
          cthdList.size() + 1, // Tự động tăng mã CTHD
          tenSP,
          soLuong,
          thanhTien,
          size,
          mauSac,
          0,
          maSP
      );

      // Thêm vào danh sách
      cthdList.add(chiTietDonHang);

      // Cập nhật tổng tiền
      updateTongTien();

    } catch (Exception e) {
      System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
    }
  }

  @FXML
  private void handleBtnXoa() {
    ChiTietDonHang selectedCTHD = tableViewCTHD.getSelectionModel().getSelectedItem();
    if (selectedCTHD != null) {
      cthdList.remove(selectedCTHD);
      updateTongTien();
    } else {
      System.out.println("Không có sản phẩm nào được chọn để xóa!");
    }
  }

  private void updateMaSPLabel() {
    String tenSP = comboBoxTenSP.getValue();
    String mauSac = comboBoxMau.getValue();
    String size = comboBoxSize.getValue();

    if (tenSP != null && mauSac != null && size != null) {
      try {
        // Lấy mã sản phẩm từ database dựa trên tên sản phẩm, màu sắc và kích thước
        String maSP = sanPhamBLL.getMaSPByTenSPMauSize(tenSP, mauSac, size);
        lblMaSP.setText(maSP);  // Cập nhật mã sản phẩm lên Label
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // Cập nhật giá sản phẩm hiển thị
  private void updateGiaSPLabel() {
    try {
      int quantity = Integer.parseInt(textFieldSoluong.getText());
      double totalPrice = currentPrice * quantity;

      // Sử dụng DecimalFormat để định dạng giá tiền
      DecimalFormat formatter = new DecimalFormat("#,###");
      lblGiaSP.setText(formatter.format(totalPrice) + " VNĐ");
    } catch (NumberFormatException e) {
      lblGiaSP.setText("0 VNĐ");
    }
  }
  private void updateTongTien() {
    double tongTien = 0;

    for (ChiTietDonHang cthd : cthdList) {
      tongTien += cthd.getGia();
    }

    // Hiển thị tổng tiền với định dạng 3 chữ số
    DecimalFormat formatter = new DecimalFormat("#,###");
    textThanhTien.setText(formatter.format(tongTien) + " VNĐ");
  }

  @FXML
  private void handleSDTChange() {
    String sdt = textFieldSDT.getText();

    if (sdt != null && !sdt.isEmpty()) {
      KhachHang khachHang = donHangBLL.timKhachHangTheoSDT(sdt);

      if (khachHang != null) {
        lblMaKH.setText(String.valueOf(khachHang.getMaKH()));
        lblTenKH.setText(khachHang.getTenKH());
      } else {
        lblMaKH.setText("N/A");
        lblTenKH.setText("Khách mới");
      }
    } else {
      lblMaKH.setText("N/A");
      lblTenKH.setText("Khách mới");
    }
  }

  @FXML
  private void handleThanhToan() {
    if (cthdList.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Cảnh báo");
      alert.setHeaderText("Chưa có sản phẩm nào trong hóa đơn");
      alert.setContentText("Vui lòng thêm sản phẩm trước khi thanh toán.");
      alert.showAndWait();
      return;
    }

    // Kiểm tra nếu currentNhanVien là null
    if (currentNhanVien == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Chưa đăng nhập");
      alert.setContentText("Vui lòng đăng nhập để thực hiện thanh toán.");
      alert.showAndWait();
      return;
    }

    // Lấy mã khách hàng từ lblMaKH
    String maKHText = lblMaKH.getText();
    int maKH = -1; // Giá trị mặc định nếu mã khách hàng không hợp lệ
    if (!maKHText.equals("N/A") && !maKHText.isEmpty()) {
      try {
        maKH = Integer.parseInt(maKHText);  // Chuyển đổi thành int
      } catch (NumberFormatException e) {
        System.out.println("Mã khách hàng không hợp lệ!");
        return;  // Nếu mã khách hàng không hợp lệ, dừng lại
      }
    }

    NhanVien nhanVien = currentNhanVien;
    double tongTien = 0;
    for (ChiTietDonHang chiTiet : cthdList) {
      tongTien += chiTiet.getGia();
    }

    Timestamp ngayTao = new Timestamp(System.currentTimeMillis());
    DonHang donHang = new DonHang(0, "Tên khách hàng", nhanVien.getTenNV(), ngayTao, tongTien, maKH, nhanVien.getMaNV());

    // Nếu mã khách hàng hợp lệ, set vào DonHang
    if (maKH != -1) {
      donHang.setMaKH(maKH);
    } else {
      System.out.println("Không có mã khách hàng hợp lệ.");
      return;
    }

    // Sử dụng BLL để thêm đơn hàng và chi tiết đơn hàng
    donHangBLL.themDonHang(donHang, cthdList);

    cthdList.clear();
    tableViewCTHD.refresh();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Thanh toán thành công");
    alert.setHeaderText(null);
    alert.setContentText("Hóa đơn đã được tạo thành công!");
    alert.showAndWait();
  }

}
