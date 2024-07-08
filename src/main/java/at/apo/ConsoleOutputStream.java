package at.apo;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.OutputStream;

public class ConsoleOutputStream extends OutputStream {
    private TextArea console;

    public ConsoleOutputStream(TextArea console) {
        this.console = console;
    }

    @Override
    public void write(int b) {
        Platform.runLater(() -> console.appendText(String.valueOf((char) b)));
    }

    @Override
    public void write(byte[] b, int off, int len) {
        String text = new String(b, off, len);
        Platform.runLater(() -> console.appendText(text));
    }
}
