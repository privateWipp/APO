module at.apo.apo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens at.apo to javafx.fxml;
    exports at.apo;
}