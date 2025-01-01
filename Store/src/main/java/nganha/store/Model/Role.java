package nganha.store.Model;

public enum Role {
  ADMIN, STAFF;

  public static Role fromString(String roleStr) {
    if (roleStr == null || roleStr.trim().isEmpty()) {
      return null; // Trả về null nếu role là null hoặc chuỗi rỗng
    }
    String normalizedRole = roleStr.trim().toUpperCase(); // Chuẩn hóa chuỗi
    switch (normalizedRole) {
      case "ADMIN":
        return ADMIN;
      case "STAFF":
        return STAFF;
      default:
        return null;
    }
  }
}
