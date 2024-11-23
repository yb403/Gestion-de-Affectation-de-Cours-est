module com.est.sb.estgi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.est.sb.estgi to javafx.fxml;
    exports com.est.sb.estgi;
    exports com.est.sb.estgi.Dashboard;
    opens com.est.sb.estgi.Dashboard to javafx.fxml;
    exports com.est.sb.estgi.Dashboard.admin;
    opens com.est.sb.estgi.Dashboard.admin to javafx.fxml;
    exports com.est.sb.estgi.actors;
    opens com.est.sb.estgi.actors to javafx.fxml;
}