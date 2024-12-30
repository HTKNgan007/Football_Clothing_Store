package nganha.store.Model;

public class ChiTietDonHang {
  private int maCTDH;
  private String tenSP;
  private int soLuong;
  private double gia;
  private String size;
  private String mauSac;

  public ChiTietDonHang(int maCTDH, String tenSP, int soLuong, double gia, String size, String mauSac) {
    this.maCTDH = maCTDH;
    this.tenSP = tenSP;
    this.soLuong = soLuong;
    this.gia = gia;
    this.size = size;
    this.mauSac = mauSac;
  }

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
}
