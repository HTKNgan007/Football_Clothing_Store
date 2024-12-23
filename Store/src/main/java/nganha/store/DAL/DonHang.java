package nganha.store.DAL;

import java.util.Date;

public class DonHang {
  private int maDH;
  private int maNV;
  private int maSP;
  private Date ngayMua;
  private int soLuong;

  public DonHang(int maDH, int maNV, int maSP, Date ngayMua, int soLuong) {
    this.maDH = maDH;
    this.maNV = maNV;
    this.maSP = maSP;
    this.ngayMua = ngayMua;
    this.soLuong = soLuong;
  }

  public int getMaDH() {
    return maDH;
  }

  public void setMaDH(int maDH) {
    this.maDH = maDH;
  }

  public int getMaNV() {
    return maNV;
  }

  public void setMaNV(int maNV) {
    this.maNV = maNV;
  }

  public int getMaSP() {
    return maSP;
  }

  public void setMaSP(int maSP) {
    this.maSP = maSP;
  }

  public Date getNgayMua() {
    return ngayMua;
  }

  public void setNgayMua(Date ngayMua) {
    this.ngayMua = ngayMua;
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
  }
}
