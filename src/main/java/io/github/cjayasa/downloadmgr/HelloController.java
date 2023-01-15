package io.github.cjayasa.downloadmgr;

import io.github.cjayasa.downloadmgr.downloader.Downloader;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.net.URI;
import java.net.URL;

public class HelloController {
    @FXML
    public MFXTextField fileUrl;
    public Label progressLabel;
    public ProgressBar downloadProgress;
    @FXML
    private Label welcomeText;

    double progress = 0;

    @FXML
    protected void onDownloadButtonClick() {
        try {
            URI fileUrlValue;
            if (fileUrl.getText().isBlank()) {
                fileUrlValue = new URL("https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGNhcnN8ZW58MHx8MHx8&w=1000&q=80").toURI();
            } else {
                fileUrlValue = new URL(fileUrl.getText()).toURI();
            }
            Downloader downloader = new Downloader(fileUrlValue.toString());
            downloader.run();
        } catch (Exception e) {
            System.out.println("Exception - Downloader");
        }
    }

    @FXML
    protected void onPasteButtonClick() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            System.out.println(clipboard.getData(DataFlavor.stringFlavor));
            fileUrl.setText(clipboard.getData(DataFlavor.stringFlavor).toString());
        } catch (Exception e) {
            System.out.println("Exception - Clipboard");
        }
    }
}