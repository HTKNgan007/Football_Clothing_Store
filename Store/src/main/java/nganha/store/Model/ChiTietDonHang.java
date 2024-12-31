package nganha.store.Model;

public class ChiTietDonHang {
  private int maCTDH;
  private String tenSP;
  private int soLuong;
  private double gia;
  private String size;
  private String mauSac;
  private int maDH;
  private int maSP;

  public ChiTietDonHang(int maCTDH, String tenSP, int soLuong, double gia, String size, String mauSac) {
    this.maCTDH = maCTDH;
    this.tenSP = tenSP;
    this.soLuong = soLuong;
    this.gia = gia;
    this.size = size;
    this.mauSac = mauSac;
  }

  public ChiTietDonHang(int maCTDH, int maDH, int maSP, int soLuong, double gia) {
    this.maCTDH = maCTDH;
    this.soLuong = soLuong;
    this.gia = gia;
    this.maDH = maDH;
    this.maSP = maSP;
  }

  public ChiTietDonHang(int maCTDH, String tenSP, int soLuong, double gia, String size, String mauSac, int maDH, int maSP) {
    this.maCTDH = maCTDH;
    this.tenSP = tenSP;
    this.soLuong = soLuong;
    this.gia = gia;
    this.size = size;
    this.mauSac = mauSac;
    this.maDH = maDH;
    this.maSP = maSP;
  }

  public ChiTietDonHang(){}

  public int getMaCTDH() {
    return maCTDH;
  }

  public void setMaCTDH(int maCTDH) {
    this.maCTDH = maCTDH;
  }

  public String getTenSP() {
    return tenSP;
  }

  public void setTenSP(String tenSP) {
    this.tenSP = tenSP;
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

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getMauSac() {
    return mauSac;
  }

  public void setMauSac(String mauSac) {
    this.mauSac = mauSac;
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
}
