package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import ClassFiles.User;
import Exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
public class LoginSignupController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "passW0rd";
    private final Connection con;
    {
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private final ArrayList<Expense> Expenses = new ArrayList<>();
    private final User activeUser = new User("", "", Expenses);
    private static final File userFilePath = new File("src/DataFiles/User.txt");
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;
    static void checkUsername(String usernameInput) throws InvalidUsernameException, FileNotFoundException {
        if (usernameInput.equals("")) {
            throw new InvalidUsernameException("Invalid username");
        }
    }

    public void login(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../LoginPage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void signup(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../SignupPage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void checkLogin(ActionEvent e) throws NullPointerException, IOException, SQLException {
        String user = userField.getText();
        String pass = passField.getText();
        String query = "SELECT username, pass FROM users WHERE username=?";
        PreparedStatement checkLogin = con.prepareStatement(query);
        checkLogin.setString(1, user);
        ResultSet rs = checkLogin.executeQuery();
        if (rs.next()) {
            if (pass.equals(rs.getString(2))) {
                FileWriter fileWriter = new FileWriter(new File("src/DataFiles/ActiveUser.txt") , false);
                fileWriter.write(user);
                fileWriter.close();
                root = FXMLLoader.load(getClass().getResource("../MainPage.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Incorrect Password");
            }
        } else {
            System.out.println("Incorrect Username");
        }
    }
    public void checkSignup(ActionEvent e) throws IOException, SQLException {
        String user = userField.getText();
        String pass = passField.getText();
        String query = "SELECT username, pass FROM users WHERE username=?";
        PreparedStatement checkLogin = con.prepareStatement(query);
        checkLogin.setString(1, user);
        ResultSet rs = checkLogin.executeQuery();
        if (rs.next()) {
            System.out.println("Username has been taken");
        } else {
            String insertUser = "INSERT INTO users (username,pass) VALUES (?,?)";
            PreparedStatement st = con.prepareStatement(insertUser);
            st.setString(1, user);
            st.setString(2, pass);
            st.executeUpdate();
            String createUserTable = "CREATE TABLE " + user + " (id SERIAL NOT NULL, total NUMERIC(20,2) NOT NULL, name TEXT NOT NULL, type TEXT NOT NULL, PRIMARY KEY (id));";
            Statement createTable = con.createStatement();
            createTable.executeUpdate(createUserTable);
            FileWriter fileWriter = new FileWriter(new File("src/DataFiles/ActiveUser.txt") , false);
            fileWriter.write(user);
            fileWriter.close();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../MainPage.fxml")));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
