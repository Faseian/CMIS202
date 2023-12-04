package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import ClassFiles.User;
import Exceptions.InvalidUsernameException;
import javafx.application.Platform;
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
    static final String url = "jdbc:postgresql://bv9lzxoemi1fludk9lwn-postgresql.services.clever-cloud.com:50013/bv9lzxoemi1fludk9lwn";
    private static final String username = "uvk3fo0h0eqky8che5ri";
    private static final String password = "HYdwJTABjCKurd43GiQMBfXlMsE7ZA";
    static Connection con;
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
            try {
                Statement createTable = con.createStatement();
                createTable.executeUpdate(createUserTable);
            } catch (SQLException e1) {
                System.out.println(e1);
            }
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
    public void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../TitlePage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void close(ActionEvent e) throws SQLException {
        con.close();
        Platform.exit();
    }
}
