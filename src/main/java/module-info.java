module com.heshanthenura.libro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.heshanthenura.libro to javafx.graphics;
    opens com.heshanthenura.libro.controllers to javafx.fxml;
    opens com.heshanthenura.libro.services to javafx.base, java.sql;

    exports com.heshanthenura.libro;
    exports com.heshanthenura.libro.controllers;
    opens com.heshanthenura.libro.services.database to java.sql, javafx.base;
}
