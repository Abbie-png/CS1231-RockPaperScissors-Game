module com.example.Game.rps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;



    exports GameUI;
    opens GameUI to javafx.fxml;
}