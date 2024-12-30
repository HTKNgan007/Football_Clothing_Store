package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nganha.store.BLL.SanPhamBLL;

import java.text.DecimalFormat;
import java.util.List;

public class HoaDonController {
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
  private Label llblGiaSP;

  // Giá của sản phẩm khi thêm sp right
  private double currentPrice = 0;

  private SanPhamBLL sanPhamBLL = new SanPhamBLL();

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

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Cập nhật giá sản phẩm hiển thị
  private void updateGiaSPLabel() {
    try {
      int quantity = Integer.parseInt(textFieldSoluong.getText());
      double totalPrice = currentPrice * quantity;

      // Sử dụng DecimalFormat để định dạng giá tiền
      DecimalFormat formatter = new DecimalFormat("#,###");
      llblGiaSP.setText(formatter.format(totalPrice) + " VNĐ");
    } catch (NumberFormatException e) {
      llblGiaSP.setText("0 VNĐ");
    }
  }
}
