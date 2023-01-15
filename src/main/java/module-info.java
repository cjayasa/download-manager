module io.github.cjayasa.downloadmgr {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens io.github.cjayasa.downloadmgr to javafx.fxml;
    exports io.github.cjayasa.downloadmgr;
}