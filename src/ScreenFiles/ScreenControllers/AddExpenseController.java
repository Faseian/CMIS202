package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import ClassFiles.User;
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
import java.util.ArrayList;

public class AddExpenseController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField ExpenseTotal;
    @FXML
    private TextField ExpenseName;
    @FXML
    private TextField ExpenseLocation;
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private User activeUser = new User("","", null);
    public Expense createExpense() {
        double total = Double.parseDouble(ExpenseTotal.getText());
        String name = ExpenseName.getText();
        String location = ExpenseLocation.getText();
        Expense newExpense = new Expense(total, name, location);

        return newExpense;
    }
    public void addExpenseToAcc(ActionEvent e) throws IOException {
        Expense expense = createExpense();
        activeUser.addExpense(expenseList, expense);
        File userFile = new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\" + activeUser.getUsername() + ".txt");
        FileWriter fileWriter = new FileWriter(userFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(expense.getTotal() + "," + expense.getExpenseName() + "," + expense.getExpenseLocation());

        root = FXMLLoader.load(getClass().getResource("../MainPage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void testScript(ActionEvent e) throws IOException {
        Expense expense = createExpense();
        activeUser.addExpense(expenseList, expense);
        if (activeUser.getUsername().equals(null)) {
            System.out.println("Username is null");
        }
        System.out.println(activeUser.getUsername());
    }
}
