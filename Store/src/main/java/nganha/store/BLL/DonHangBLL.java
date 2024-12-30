package nganha.store.BLL;

import nganha.store.DAL.ChiTietDonHangDAL;
import nganha.store.DAL.DonHangDAL;
import nganha.store.Model.*;

import java.sql.SQLException;
import java.util.List;

public class DonHangBLL {
  private DonHangDAL donHangDAL = new DonHangDAL();
  private ChiTietDonHangDAL chiTietDonHangDAL = new ChiTietDonHangDAL();


  public List<DonHang> getAllDonHang() throws SQLException, ClassNotFoundException {
    return donHangDAL.getAllDonHang();
  }

  // Thêm đơn hàng
  public int themDonHang(DonHang donHang, List<ChiTietDonHang> cthdList) {
    int maDH = donHangDAL.themDonHang(donHang);  // Thêm đơn hàng và lấy mã đơn hàng
    if (maDH != -1) {
      // Thêm chi tiết đơn hàng
      for (ChiTietDonHang cthd : cthdList) {
        ChiTietDonHangCSDL chiTietDonHangCSDL = new ChiTietDonHangCSDL(
            0, // Mã chi tiết đơn hàng tự động tăng
            maDH, // Mã đơn hàng
            cthd.getMaSP(), // Mã sản phẩm
            cthd.getSoLuong(), // Số lượng
            cthd.getGia() // Giá
        );
        chiTietDonHangDAL.themChiTietDonHang(chiTietDonHangCSDL); // Thêm vào CSDL
      }
    }
    return maDH;
  }

  public KhachHang timKhachHangTheoSDT(String sdt) {
    return donHangDAL.timKhachHangTheoSDT(sdt);
  }
}
