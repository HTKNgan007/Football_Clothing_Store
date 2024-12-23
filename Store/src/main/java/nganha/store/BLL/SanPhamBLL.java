package nganha.store.BLL;

import nganha.store.DAL.SanPhamDAL;
import nganha.store.Model.SanPham;

import java.sql.SQLException;
import java.util.List;

public class SanPhamBLL {
  public static List<SanPham> getAllSanPham() throws SQLException, ClassNotFoundException {
    return SanPhamDAL.getAllSanPham();
  }

  public static void addSanPham(SanPham sp) throws SQLException, ClassNotFoundException {
    SanPhamDAL.addSanPham(sp);
  }

  public static void updateSanPham(SanPham sp) throws SQLException, ClassNotFoundException {
    SanPhamDAL.updateSanPham(sp);
  }

  public static void deleteSanPham(String maSP) throws SQLException, ClassNotFoundException {
    SanPhamDAL.deleteSanPham(maSP);
  }
}
