package nganha.store.Model;

import java.sql.Timestamp;

public class DonHang {
  private int maDH;
  private String tenKH;
  private String tenNV;
  private Timestamp ngayTao;
  private double tongTien;

  public DonHang(int maDH, String tenKH, String tenNV, Timestamp ngayTao, double tongTien) {
    this.maDH = maDH;
    this.tenKH = tenKH;
    this.tenNV = tenNV;
    this.ngayTao = ngayTao;
    this.tongTien = tongTien;
  }

  public int getMaDH() {
    return maDH;
  }

  public void setMaDH(int maDH) {
    this.maDH = maDH;
  }

  public String getTenKH() {
    return tenKH;
  }

  public void setTenKH(String tenKH) {
    this.tenKH = tenKH;
  }

  public String getTenNV() {
    return tenNV;
  }

  public void setTenNV(String tenNV) {
    this.tenNV = tenNV;
  }

  public Timestamp getNgayTao() {
    return ngayTao;
  }

  public void setNgayTao(Timestamp ngayTao) {
    this.ngayTao = ngayTao;
  }

  public double getTongTien() {
    return tongTien;
  }

  public void setTongTien(double tongTien) {
    this.tongTien = tongTien;
  }
}
