package nganha.store.BLL;

import nganha.store.DAL.ChiTietDonHangDAL;
import nganha.store.Model.ChiTietDonHang;

import java.sql.SQLException;
import java.util.List;

public class ChiTietDonHangBLL {
  private ChiTietDonHangDAL chiTietDonHangDAL = new ChiTietDonHangDAL();

  public List<ChiTietDonHang> getChiTietDonHangByMaDH(int maDH) throws SQLException, ClassNotFoundException {
    return chiTietDonHangDAL.getChiTietDonHangByMaDH(maDH);
  }
}
