package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import ClassFiles.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddExpenseController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField ExpenseTotal;
    @FXML
    private TextField ExpenseName;
    @FXML
    private ChoiceBox<String> expenseType;
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private User activeUser = new User("", "", expenseList);
    private String[] expenseTypes = {"Automotive", "Clothing", "Education", "Entertainment", "Gasoline", "Groceries", "Home", "Medical", "Restaurants", "Services", "Misc"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expenseType.getItems().addAll(expenseTypes);
    }
    public Expense createExpense() {
        double total = Double.parseDouble(ExpenseTotal.getText());
        String name = ExpenseName.getText();
        String type = expenseType.getValue();
        Expense newExpense = new Expense(total, name, type);
        return newExpense;
    }
    public void addExpenseToAcc(ActionEvent e) throws IOException {
        Scanner scanner = new Scanner(new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\ActiveUser.txt"));
        String user = scanner.nextLine();
        Expense expense = createExpense();
        File userFile = new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\UserFiles\\" + user + ".txt");
        FileWriter fileWriter = new FileWriter(userFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(expense.getTotal() + "," + expense.getExpenseName() + "," + expense.getExpenseType());
        printWriter.close();
        System.out.println("Item added");
        root = FXMLLoader.load(getClass().getResource("../MainPage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
