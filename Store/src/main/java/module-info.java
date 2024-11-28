module nganha.store {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires org.kordamp.bootstrapfx.core;
  requires java.sql;

  opens nganha.store to javafx.fxml;
  exports nganha.store;
  opens nganha.store.Controller to javafx.fxml;
  exports nganha.store.Controller;
}