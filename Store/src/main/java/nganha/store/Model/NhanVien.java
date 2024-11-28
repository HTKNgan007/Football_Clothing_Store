package nganha.store.Model;

public class NhanVien {
  private int maNV;
  private String tenNV;
  private int SDT;
  private String username;
  private String password;

  public NhanVien(int maNV, String tenNV, int SDT, String username, String password) {
    this.maNV = maNV;
    this.tenNV = tenNV;
    this.SDT = SDT;
    this.username = username;
    this.password = password;
  }
  public NhanVien() {}

  public int getMaNV() {
    return maNV;
  }

  public void setMaNV(int maNV) {
    this.maNV = maNV;
  }

  public String getTenNV() {
    return tenNV;
  }

  public void setTenNV(String tenNV) {
    this.tenNV = tenNV;
  }

  public int getSDT() {
    return SDT;
  }

  public void setSDT(int SDT) {
    this.SDT = SDT;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
