module nganha.store {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires org.kordamp.bootstrapfx.core;

  opens nganha.store to javafx.fxml;
  exports nganha.store;
}