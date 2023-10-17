package ScreenFiles.ScreenControllers;
import Exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class LoginSignupController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private final File userFilePath = new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\User.txt");
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;

    static void checkUsername(String usernameInput) throws InvalidUsernameException {
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

    public void checkLogin(ActionEvent e) throws IOException {
        String user = userField.getText();
        String pass = passField.getText();

        if (userFilePath.exists()) {
            Scanner loginFileText = new Scanner(userFilePath);
            do {
                String lineCatcher = loginFileText.nextLine();
                int holder = lineCatcher.indexOf(",");
                String inputPass = lineCatcher.substring(holder + 1);
                String inputUser = lineCatcher.substring(0, holder);
                if (user.equals(inputUser)) {
                    if (pass.equals(inputPass)) {
                        root = FXMLLoader.load(getClass().getResource("../MainPage.fxml"));
                        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            } while (loginFileText.hasNext());
        }
    }

    public void checkSignup(ActionEvent e) throws IOException {
        String user = userField.getText();
        String pass = passField.getText();

        try {
            checkUsername(user);
            FileWriter fileWriter = new FileWriter(userFilePath, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            if(!userFilePath.exists()) {
                userFilePath.createNewFile();
            }

            printWriter.println(user + "," + pass);
            printWriter.close();

            root = FXMLLoader.load(getClass().getResource("../TitlePage.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            System.out.println("A problem has occurred: " + exception);
        }
    }
}
