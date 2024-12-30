package nganha.store.BLL;

import nganha.store.DAL.DonHangDAL;
import nganha.store.Model.DonHang;

import java.sql.SQLException;
import java.util.List;

public class DonHangBLL {
  private DonHangDAL donHangDAL = new DonHangDAL();

  public List<DonHang> getAllDonHang() throws SQLException, ClassNotFoundException {
    return donHangDAL.getAllDonHang();
  }
}
