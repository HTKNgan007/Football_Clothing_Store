package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nganha.store.BLL.ChiTietDonHangBLL;
import nganha.store.BLL.DonHangBLL;
import nganha.store.Model.ChiTietDonHang;
import nganha.store.Model.DonHang;

public class QuanLyDonHangController {
  @FXML
  private TableView<DonHang> tableDonHang;

  @FXML
  private TableView<ChiTietDonHang> tableChiTietDonHang;

  private ObservableList<DonHang> donHangList = FXCollections.observableArrayList();
  private ObservableList<ChiTietDonHang> chiTietDonHangList = FXCollections.observableArrayList();

  private DonHangBLL donHangBLL = new DonHangBLL();
  private ChiTietDonHangBLL chiTietDonHangBLL = new ChiTietDonHangBLL();

  @FXML
  public void initialize() {
    // Cấu hình bảng DonHang
    tableDonHang.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("maDH"));
    tableDonHang.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("maKH"));
    tableDonHang.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("maNV"));
    tableDonHang.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
    tableDonHang.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("tongTien"));

    // Cấu hình bảng ChiTietDonHang
    tableChiTietDonHang.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("maSP"));
    tableChiTietDonHang.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    tableChiTietDonHang.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gia"));

    // Load dữ liệu
    try {
      donHangList.addAll(donHangBLL.getAllDonHang());
    } catch (Exception e) {
      e.printStackTrace();
    }

    tableDonHang.setItems(donHangList);

    // Lắng nghe sự kiện chọn dòng
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
}
