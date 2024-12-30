package nganha.store.Model;

public class ChiTietDonHangCSDL {
  private int maCTDH;
  private int maDH;
  private int maSP;
  private int soLuong;
  private double gia;

  public ChiTietDonHangCSDL(int maCTDH, int maDH, int maSP, int soLuong, double gia) {
    this.maCTDH = maCTDH;
    this.maDH = maDH;
    this.maSP = maSP;
    this.soLuong = soLuong;
    this.gia = gia;
  }
  public ChiTietDonHangCSDL(){}

  public int getMaCTDH() {
    return maCTDH;
  }

  public void setMaCTDH(int maCTDH) {
    this.maCTDH = maCTDH;
  }

  public int getMaDH() {
    return maDH;
  }

  public void setMaDH(int maDH) {
    this.maDH = maDH;
  }

  public int getMaSP() {
    return maSP;
  }

  public void setMaSP(int maSP) {
    this.maSP = maSP;
  }

  public int getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(int soLuong) {
    this.soLuong = soLuong;
  }

  public double getGia() {
    return gia;
  }

  public void setGia(double gia) {
    this.gia = gia;
  }
}
