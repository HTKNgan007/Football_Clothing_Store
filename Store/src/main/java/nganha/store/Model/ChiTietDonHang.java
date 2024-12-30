package nganha.store.Model;

public class ChiTietDonHang {
  private int maCTDH;
  private String tenSP;
  private int soLuong;
  private double gia;

  public ChiTietDonHang(int maCTDH, String tenSP, int soLuong, double gia) {
    this.maCTDH = maCTDH;
    this.tenSP = tenSP;
    this.soLuong = soLuong;
    this.gia = gia;
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
}
