package nganha.store.BLL;

import nganha.store.DAL.KhachHangDAL;
import nganha.store.Model.KhachHang;

import java.sql.SQLException;
import java.util.List;

public class KhachHangBLL {
  private final KhachHangDAL khachHangDAL;

  public KhachHangBLL() {
    this.khachHangDAL = new KhachHangDAL();
  }

  public List<KhachHang> getAllKhachHang() throws SQLException, ClassNotFoundException {
    return khachHangDAL.getAllKhachHang();
  }

  // Thêm khách hàng với kiểm tra nghiệp vụ
  public void addKhachHang(KhachHang khachHang) throws Exception {
    if (khachHang.getTenKH() == null || khachHang.getTenKH().trim().isEmpty()) {
      throw new Exception("Tên khách hàng không được để trống.");
    }
    if (khachHang.getSDT() == null || khachHang.getSDT().trim().isEmpty()) {
      throw new Exception("SDT khách hàng không được để trống.");
    }

    // Gọi DAL để thêm khách hàng
    khachHangDAL.addKhachHang(khachHang);
  }

  // Cập nhật khách hàng với kiểm tra nghiệp vụ
  public boolean updateKhachHang(KhachHang khachHang) throws Exception {
    try {
      khachHangDAL.updateKhachHang(khachHang);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  // Xóa khách hàng
  public boolean deleteKhachHang(int maKH) throws Exception {
    try {
      return khachHangDAL.deleteKhachHang(maKH);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
