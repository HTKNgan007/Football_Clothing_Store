package nganha.store.Model;

import javafx.beans.property.*;

public class SanPham {
  private StringProperty maSP;
  private StringProperty tenSP;
  private StringProperty loaiSP;
  private DoubleProperty giaBan;
  private IntegerProperty soLuong;
  private StringProperty size;
  private StringProperty mauSac;
  private StringProperty moTa;

  // Constructor
  public SanPham(String maSP, String tenSP, String loaiSP, double giaBan, int soLuong, String size, String mauSac, String moTa) {
    this.maSP = new SimpleStringProperty(maSP);
    this.tenSP = new SimpleStringProperty(tenSP);
    this.loaiSP = new SimpleStringProperty(loaiSP);
    this.giaBan = new SimpleDoubleProperty(giaBan);
    this.soLuong = new SimpleIntegerProperty(soLuong);
    this.size = new SimpleStringProperty(size);
    this.mauSac = new SimpleStringProperty(mauSac);
    this.moTa = new SimpleStringProperty(moTa);
  }

  // Getters và Setters cho các Property
  public StringProperty maSPProperty() {
    return maSP;
  }

  public String getMaSP() {
    return maSP.get();
  }

  public void setMaSP(String maSP) {
    this.maSP.set(maSP);
  }

  public StringProperty tenSPProperty() {
    return tenSP;
  }

  public String getTenSP() {
    return tenSP.get();
  }

  public void setTenSP(String tenSP) {
    this.tenSP.set(tenSP);
  }

  public StringProperty loaiSPProperty() {
    return loaiSP;
  }

  public String getLoaiSP() {
    return loaiSP.get();
  }

  public void setLoaiSP(String loaiSP) {
    this.loaiSP.set(loaiSP);
  }

  public DoubleProperty giaBanProperty() {
    return giaBan;
  }

  public double getGiaBan() {
    return giaBan.get();
  }

  public void setGiaBan(double giaBan) {
    this.giaBan.set(giaBan);
  }

  public IntegerProperty soLuongProperty() {
    return soLuong;
  }

  public int getSoLuong() {
    return soLuong.get();
  }

  public void setSoLuong(int soLuong) {
    this.soLuong.set(soLuong);
  }

  public StringProperty sizeProperty() {
    return size;
  }

  public String getSize() {
    return size.get();
  }

  public void setSize(String size) {
    this.size.set(size);
  }

  public StringProperty mauSacProperty() {
    return mauSac;
  }

  public String getMauSac() {
    return mauSac.get();
  }

  public void setMauSac(String mauSac) {
    this.mauSac.set(mauSac);
  }

  public StringProperty moTaProperty() {
    return moTa;
  }

  public String getMoTa() {
    return moTa.get();
  }

  public void setMoTa(String moTa) {
    this.moTa.set(moTa);
  }
}
