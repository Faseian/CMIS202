import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "passW0rd";
        Connection con = DriverManager.getConnection(url, username, password);

        Statement statement = con.createStatement();
        String query = "Select username from test where id=1";
        ResultSet rs = statement.executeQuery(query);

        Parent root = FXMLLoader.load(getClass().getResource("ScreenFiles/TitlePage.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}