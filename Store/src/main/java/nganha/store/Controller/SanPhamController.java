package nganha.store.Controller;

import javafx.scene.control.cell.PropertyValueFactory;
import nganha.store.BLL.SanPhamBLL;
import nganha.store.Model.SanPham;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class SanPhamController {
  @FXML
  private TableView<SanPham> tblSanPham;
  @FXML
  private TableColumn<SanPham, String> colMaSP;
  @FXML
  private TableColumn<SanPham, String> colTenSP;
  @FXML
  private TableColumn<SanPham, String> colLoaiSP;
  @FXML
  private TableColumn<SanPham, Double> colGiaBan;
  @FXML
  private TableColumn<SanPham, Integer> colSoLuong;
  @FXML
  private TableColumn<SanPham, String> colSize;
  @FXML
  private TableColumn<SanPham, String> colMauSac;
  @FXML
  private TableColumn<SanPham, String> colMoTa;

  @FXML
  private TextField txtMaSP;
  @FXML
  private TextField txtTenSP;
  @FXML
  private TextField txtLoaiSP;
  @FXML
  private TextField txtGiaBan;
  @FXML
  private TextField txtSoLuong;
  @FXML
  private TextField txtSize;
  @FXML
  private TextField txtMauSac;
  @FXML
  private TextArea txtMoTa;

  @FXML
  public void initialize() {
    // Cài đặt các cellValueFactory cho mỗi cột trong bảng
    colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
    colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
    colLoaiSP.setCellValueFactory(new PropertyValueFactory<>("loaiSP"));
    colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
    colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
    colMauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
    colMoTa.setCellValueFactory(new PropertyValueFactory<>("moTa"));

    // Lấy danh sách sản phẩm từ BLL và hiển thị trên TableView
    try {
      List<SanPham> sanPhamList = SanPhamBLL.getAllSanPham();
      tblSanPham.getItems().setAll(sanPhamList);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void themSanPham() {
    SanPham sp = new SanPham(
        txtMaSP.getText(),
        txtTenSP.getText(),
        txtLoaiSP.getText(),
        Double.parseDouble(txtGiaBan.getText()),
        Integer.parseInt(txtSoLuong.getText()),
        txtSize.getText(),
        txtMauSac.getText(),
        txtMoTa.getText()
    );
    try {
      SanPhamBLL.addSanPham(sp);
      initialize(); // Refresh the table
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void capNhatSanPham() {
    SanPham sp = new SanPham(
        txtMaSP.getText(),
        txtTenSP.getText(),
        txtLoaiSP.getText(),
        Double.parseDouble(txtGiaBan.getText()),
        Integer.parseInt(txtSoLuong.getText()),
        txtSize.getText(),
        txtMauSac.getText(),
        txtMoTa.getText()
    );
    try {
      SanPhamBLL.updateSanPham(sp);
      initialize(); // Refresh the table
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void xoaSanPham() {
    String maSP = txtMaSP.getText();
    try {
      SanPhamBLL.deleteSanPham(maSP);
      initialize(); // Refresh the table
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
