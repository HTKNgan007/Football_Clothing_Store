package nganha.store.Model;

import java.sql.Timestamp;

public class DonHang {
  private int maDH;
  private int maKH;
  private int maNV;
  private Timestamp ngayTao;
  private double tongTien;

  public DonHang(int maDH, int maKH, int maNV, Timestamp ngayTao, double tongTien) {
    this.maDH = maDH;
    this.maKH = maKH;
    this.maNV = maNV;
    this.ngayTao = ngayTao;
    this.tongTien = tongTien;
  }

  public int getMaDH() {
    return maDH;
  }

  public void setMaDH(int maDH) {
    this.maDH = maDH;
  }

  public int getMaKH() {
    return maKH;
  }

  public void setMaKH(int maKH) {
    this.maKH = maKH;
  }

  public int getMaNV() {
    return maNV;
  }

  public void setMaNV(int maNV) {
    this.maNV = maNV;
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

  public void setTongTien(int tongTien) {
    this.tongTien = tongTien;
  }
}
