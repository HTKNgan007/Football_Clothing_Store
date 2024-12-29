package nganha.store.Model;

public enum Role {
  ADMIN, STAFF;

  public static Role fromString(String roleStr) {
    if (roleStr == null) {
      throw new IllegalArgumentException("Role không được null.");
    }
    switch (roleStr.toUpperCase()) { // Chuyển chuỗi thành chữ in hoa để so sánh
      case "ADMIN":
        return ADMIN;
      case "STAFF":
        return STAFF;
      default:
        throw new IllegalArgumentException("Không tìm thấy Role: " + roleStr);
    }
  }
}
