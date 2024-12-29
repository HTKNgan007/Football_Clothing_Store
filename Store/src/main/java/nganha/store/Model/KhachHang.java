package nganha.store.Model;

public class KhachHang {
  private int maKH;
  private String tenKH;
  private String SDT;
  private String diaChi;
  private String email;

  public KhachHang(int maKH, String tenKH, String SDT, String diaChi, String email) {
    this.maKH = maKH;
    this.tenKH = tenKH;
    this.SDT = SDT;
    this.diaChi = diaChi;
    this.email = email;
  }
  public KhachHang(){}

  public int getMaKH() {
    return maKH;
  }

  public void setMaKH(int maKH) {
    this.maKH = maKH;
  }

  public String getTenKH() {
    return tenKH;
  }

  public void setTenKH(String tenKH) {
    this.tenKH = tenKH;
  }

  public String getSDT() {
    return SDT;
  }

  public void setSDT(String SDT) {
    this.SDT = SDT;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
