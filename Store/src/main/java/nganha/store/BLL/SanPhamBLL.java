package nganha.store.BLL;

import nganha.store.DAL.SanPhamDAL;
import nganha.store.Model.SanPham;

import java.sql.SQLException;
import java.util.List;

public class SanPhamBLL {
  private final SanPhamDAL sanPhamDAL;

  // Constructor
  public SanPhamBLL() {
    this.sanPhamDAL = new SanPhamDAL();
  }

  public List<SanPham> getAllSanPham() throws SQLException, ClassNotFoundException {
    return sanPhamDAL.getAllSanPham();
  }

  // Thêm sản phẩm với kiểm tra nghiệp vụ
  public void addSanPham(SanPham sp) throws Exception {
    if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
      throw new Exception("Tên sản phẩm không được để trống.");
    }
    if (sp.getGiaBan() == null || sp.getGiaBan() <= 0) {
      throw new Exception("Giá bán phải lớn hơn 0.");
    }
    if (sp.getSoLuong() == null || sp.getSoLuong() < 0) {
      throw new Exception("Số lượng không được nhỏ hơn 0.");
    }
    if (sp.getSize() == null) {
      throw new Exception("Kích thước sản phẩm không được để trống.");
    }
    if (sp.getMauSac() == null || sp.getMauSac().trim().isEmpty()) {
      throw new Exception("Màu sắc sản phẩm không được để trống.");
    }

    // Gọi DAL để thêm sản phẩm
    sanPhamDAL.addSanPham(sp);
  }

  // Cập nhật sản phẩm với kiểm tra nghiệp vụ
  public boolean updateSanPham(SanPham sp) throws Exception {
    try {
      sanPhamDAL.updateSanPham(sp);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  // Xóa sản phẩm
  public boolean deleteSanPham(int maSP) throws Exception {
    try {
      return sanPhamDAL.deleteSanPham(maSP);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  //Làm việc với đơn hàng
  public List<String> getTenSanPhamDistinct() throws SQLException, ClassNotFoundException {
    return sanPhamDAL.getTenSanPhamDistinct();
  }

  public List<String> getMauSacByTenSP(String tenSP) throws SQLException, ClassNotFoundException {
    return sanPhamDAL.getMauSacByTenSP(tenSP);
  }

  public List<String> getSizeByTenSPAndMau(String tenSP, String mauSac) throws SQLException, ClassNotFoundException {
    return sanPhamDAL.getSizeByTenSPAndMau(tenSP, mauSac);
  }
}
