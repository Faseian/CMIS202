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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddExpenseController implements Initializable {
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
    @FXML
    private TextField ExpenseTotal;
    @FXML
    private TextField ExpenseName;
    @FXML
    private ChoiceBox<String> expenseType;
    private final ArrayList<Expense> expenseList = new ArrayList<>();
    private final User activeUser = new User("", "", expenseList);
    private final String[] expenseTypes = {"Automotive", "Clothing", "Education", "Entertainment", "Gasoline", "Groceries", "Home", "Medical", "Restaurants", "Services", "Misc"};
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
    public void addExpenseToAcc(ActionEvent e) throws IOException, SQLException {
        Scanner scanner = new Scanner(new File("src/DataFiles/ActiveUser.txt"));
        String user = scanner.nextLine();
        String insertExpense = "INSERT INTO " + user + " (total, name, type) VALUES (?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(insertExpense);
        Expense expense = createExpense();
        System.out.println("Expense total, name, and type " + expense.getTotal() + " " + expense.getExpenseName() + " " + expense.getExpenseType());
        if (expense.getTotal() < 0) {
            System.out.println("Invalid total");
        } else {
            try {
                preparedStatement.setDouble(1, expense.getTotal());
                preparedStatement.setString(2, expense.getExpenseName());
                preparedStatement.setString(3, expense.getExpenseType());
                preparedStatement.executeUpdate();
                root = FXMLLoader.load(getClass().getResource("../MainPage.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e1) {
                System.out.println("Error: " + e1);
            }
        }
    }
}
