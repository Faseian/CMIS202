package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import Exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainScreenController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    LinkedList<Expense> queue = new LinkedList<>();

    public void addExpensePage (ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../AddExpensePage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scanner activeUserSheet = null;
        Scanner scanner = null;
        try {
            activeUserSheet = new Scanner(new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\ActiveUser.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String lineHolder = activeUserSheet.nextLine();
        int placeHolder = lineHolder.indexOf(",");
        String user = lineHolder.substring(0,placeHolder);
        try {
            scanner = new Scanner(new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\UserFiles\\" + user + ".txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] expenseConstruct = line.split(",");
            Expense expense = new Expense(Double.parseDouble(expenseConstruct[0]), expenseConstruct[1], expenseConstruct[2]);
            queue.add(expense);
        }
    }
}
