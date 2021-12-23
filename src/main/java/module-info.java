module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

<<<<<<< HEAD
    opens com.example.demo to javafx.fxml;
=======

    opens com.example.demo to javafx.fxml;

>>>>>>> bd81f1f5a39afbeafaa4a2d129de0504044d976f
    exports com.example.demo;
}