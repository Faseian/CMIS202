import ClassFiles.ConnectionUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        ConnectionUtil db = new ConnectionUtil();
        Connection conn = db.connect_to_db("postgres","postgres"," ");
        db.createTable(conn, "recovery");
        Parent root = FXMLLoader.load(getClass().getResource("ScreenFiles/TitlePage.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}