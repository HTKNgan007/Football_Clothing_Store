package nganha.store.Model;

public class NhanVien {
  public enum Role {
    ADMIN, STAFF
  }
  private int maNV;
  private String tenNV;
  private String SDT;
  private String username;
  private String password;
  private Role role;
  private String email;

  public NhanVien(int maNV, String tenNV, String SDT, String username, String password, Role role, String email) {
    this.maNV = maNV;
    this.tenNV = tenNV;
    this.SDT = SDT;
    this.username = username;
    this.password = password;
    this.role = role;
    this.email = email;
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

  public String getSDT() {
    return SDT;
  }

  public void setSDT(String SDT) {
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
