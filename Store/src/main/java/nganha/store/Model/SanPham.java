package nganha.store.Model;

import javafx.beans.property.*;

public class SanPham {
  public enum Size {
    S, M, L, XL, XXL
  }
  private int maSP;
  private String tenSP;
  private Double giaBan;
  private Integer soLuong;
  private Size size;
  private String mauSac;
  private String moTa;

  public SanPham(int maSP, String tenSP, Double giaBan, Integer soLuong, Size size, String mauSac, String moTa) {
    this.maSP = maSP;
    this.tenSP = tenSP;
    this.giaBan = giaBan;
    this.soLuong = soLuong;
    this.size = size;
    this.mauSac = mauSac;
    this.moTa = moTa;
  }
  public SanPham() {}

  public int getMaSP() {
    return maSP;
  }

  public void setMaSP(int maSP) {
    this.maSP = maSP;
  }

  public String getTenSP() {
    return tenSP;
  }

  public void setTenSP(String tenSP) {
    this.tenSP = tenSP;
  }
  public Double getGiaBan() {
    return giaBan;
  }

  public void setGiaBan(Double giaBan) {
    this.giaBan = giaBan;
  }

  public Integer getSoLuong() {
    return soLuong;
  }

  public void setSoLuong(Integer soLuong) {
    this.soLuong = soLuong;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public String getMauSac() {
    return mauSac;
  }

  public void setMauSac(String mauSac) {
    this.mauSac = mauSac;
  }

  public String getMoTa() {
    return moTa;
  }

  public void setMoTa(String moTa) {
    this.moTa = moTa;
  }
}
