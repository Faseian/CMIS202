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
import java.util.ArrayList;
import java.util.Scanner;


public class LoginSignupController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private ArrayList<Expense> Expenses = new ArrayList<>();
    private User activeUser = new User("", "", Expenses);
    private static final File userFilePath = new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\User.txt");
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

    public void checkLogin(ActionEvent e) throws NullPointerException, IOException {
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
                        FileWriter fileWriter = new FileWriter(new File("src/DataFiles/ActiveUser.txt") , false);
                        PrintWriter printWriter = new PrintWriter(fileWriter, true);
                        printWriter.print(user + "," + pass);
                        printWriter.close();
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
        boolean validUsername = true;
        try {
            checkUsername(user);
            FileWriter fileWriter = new FileWriter(userFilePath, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            if(!userFilePath.exists()) {
                userFilePath.createNewFile();
                validUsername = true;
            } else if (userFilePath.length() == 0) {
                validUsername = true;
            }else {
                Scanner loginFileText = new Scanner(userFilePath);
                do {
                    String lineCatcher = loginFileText.nextLine();
                    int holder = lineCatcher.indexOf(",");
                    String inputUser = lineCatcher.substring(0, holder);
                    if (user.equals(inputUser)) {
                        validUsername = false;
                        System.out.println("Username has been taken");
                    }
                } while (loginFileText.hasNext());
            }
            if (validUsername) {
                printWriter.println(user + "," + pass);
                printWriter.close();
                File createUserFile = new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\" + user + ".txt");
                createUserFile.createNewFile();
                FileWriter activeWriter = new FileWriter(new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\ActiveUser.txt"), false);
                PrintWriter activePrintWriter = new PrintWriter(activeWriter, true);
                activePrintWriter.print(user + "," + pass);
                activePrintWriter.close();
                activeUser.setUsername(user);
                activeUser.setPass(pass);
                root = FXMLLoader.load(getClass().getResource("../MainPage.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception exception) {
            System.out.println("A problem has occurred: " + exception);
        }
    }

    public User getActiveUser() {
        return activeUser;
    }
}
