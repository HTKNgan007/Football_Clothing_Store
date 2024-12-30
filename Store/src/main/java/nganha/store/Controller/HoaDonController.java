package nganha.store.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import nganha.store.BLL.SanPhamBLL;

import java.util.List;

public class HoaDonController {
  @FXML
  private ComboBox<String> comboBoxTenSP;
  @FXML
  private ComboBox<String> comboBoxMau;
  @FXML
  private ComboBox<String> comboBoxSize;

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

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
