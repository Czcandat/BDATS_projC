module me.bdats_projc {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.bdats_projc to javafx.fxml;
    exports me.bdats_projc;
}