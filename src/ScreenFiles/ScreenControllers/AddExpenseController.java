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
import java.util.Scanner;

public class AddExpenseController extends LoginSignupController {
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
    private User activeUser = new User("", "", false, expenseList);
    public Expense createExpense() {
        double total = Double.parseDouble(ExpenseTotal.getText());
        String name = ExpenseName.getText();
        String location = ExpenseLocation.getText();
        Expense newExpense = new Expense(total, name, location);

        return newExpense;
    }
    public void addExpenseToAcc(ActionEvent e) throws IOException {
        Scanner scanner = new Scanner(new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\ActiveUser.txt"));
        String lineHolder = scanner.nextLine();
        int placeHolder = lineHolder.indexOf(",");
        String user = lineHolder.substring(0,placeHolder);
        activeUser.setUsername(user);
        Expense expense = createExpense();
        activeUser.addExpense(expenseList, expense);
        File userFile = new File("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\" + activeUser.getUsername() + ".txt");
        FileWriter fileWriter = new FileWriter(userFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(expense.getTotal() + "," + expense.getExpenseName() + "," + expense.getExpenseLocation());
        System.out.println("Item added");
        System.out.println("C:\\Users\\nab4n\\IdeaProjects\\CMIS202\\src\\DataFiles\\" + activeUser.getUsername() + ".txt");
    }
}
