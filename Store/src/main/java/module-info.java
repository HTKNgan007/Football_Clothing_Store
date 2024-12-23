module nganha.store {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires org.kordamp.bootstrapfx.core;
  requires java.sql;
  requires java.desktop;
  requires mysql.connector.j;

  opens nganha.store to javafx.fxml;
  exports nganha.store;
  opens nganha.store.Model to javafx.base;
  exports nganha.store.Model;
  opens nganha.store.Controller to javafx.fxml;
  exports nganha.store.Controller;
}