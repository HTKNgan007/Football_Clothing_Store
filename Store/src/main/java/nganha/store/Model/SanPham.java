package nganha.store.Model;

import javafx.beans.property.*;

public class SanPham {
  private String maSP;
  private String tenSP;
  private String loaiSP;
  private Double giaBan;
  private Integer soLuong;
  private String size;
  private String mauSac;
  private String moTa;

  // Constructor


  public SanPham(String maSP, String tenSP, String loaiSP, Double giaBan, Integer soLuong, String size, String mauSac, String moTa) {
    this.maSP = maSP;
    this.tenSP = tenSP;
    this.loaiSP = loaiSP;
    this.giaBan = giaBan;
    this.soLuong = soLuong;
    this.size = size;
    this.mauSac = mauSac;
    this.moTa = moTa;
  }

  // Getters và Setters cho các Property

  public String getMaSP() {
    return maSP;
  }

  public void setMaSP(String maSP) {
    this.maSP = maSP;
  }

  public String getTenSP() {
    return tenSP;
  }

  public void setTenSP(String tenSP) {
    this.tenSP = tenSP;
  }

  public String getLoaiSP() {
    return loaiSP;
  }

  public void setLoaiSP(String loaiSP) {
    this.loaiSP = loaiSP;
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

  public String getMoTa() {
    return moTa;
  }

  public void setMoTa(String moTa) {
    this.moTa = moTa;
  }
}
