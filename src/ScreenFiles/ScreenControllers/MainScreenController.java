package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.*;

public class MainScreenController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private String[] expenseTypes = {"Automotive", "Clothing", "Education", "Entertainment", "Gasoline", "Groceries", "Home", "Medical", "Restaurants", "Services", "Misc"};
    private LinkedList<Expense> queue = new LinkedList<>();
    private TreeSet<Expense> expenseTree = new TreeSet<>();
    private Hashtable<String, Double> totalExpenses = new Hashtable<>();
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text title;
    @FXML
    private PieChart expenseChart;
    @FXML
    private ToggleGroup type;
    @FXML
    private RadioButton addAll;
    //, addAutomotive, addEducation, addEntertainment, addGasoline, addGroceries, addHome, addMedical, addRestaurants, addServices, addMisc;
    public void addExpensePage (ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../AddExpensePage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updatePieChart(ActionEvent e) {
        totalExpenses.clear();
        RadioButton radioButton = (RadioButton) type.getSelectedToggle();
        String holder = radioButton.getText();
        if (holder.equals("All")) {
            for (int i = 0; i < queue.size(); i++) {
                expenseTree.add(queue.get(i));
                if (!totalExpenses.containsKey(queue.get(i).getExpenseType())) {
                    totalExpenses.put(queue.get(i).getExpenseType(), queue.get(i).getTotal());
                } else {
                    double newAmount = totalExpenses.get(queue.get(i).getExpenseType()) + queue.get(i).getTotal();
                    totalExpenses.replace(queue.get(i).getExpenseType(), newAmount);
                }
            }
            ObservableList<PieChart.Data> expenseChartData = FXCollections.observableArrayList();
            for(int i = 0; i < expenseTypes.length; i++) {
                if(totalExpenses.containsKey(expenseTypes[i])) {
                    expenseChartData.add(new PieChart.Data(expenseTypes[i], totalExpenses.get(expenseTypes[i])));
                }
            }
            expenseChart = new PieChart(expenseChartData);
            expenseChart.setClockwise(true);
            expenseChart.setLabelLineLength(75);
            expenseChart.setLabelsVisible(true);
            expenseChart.setStartAngle(180);
            borderPane.setCenter(expenseChart);
        } else {
                for (Expense expense : queue) {
                    if (expense.getExpenseType().equals(holder)) {
                        totalExpenses.put(expense.getExpenseName(), expense.getTotal());
                    }
                }
                String[] array = totalExpenses.keySet().toArray(new String[totalExpenses.size()]);
                if (!(array.length == 0)) {
                    ObservableList<PieChart.Data> expenseChartData = FXCollections.observableArrayList();
                    for (int i = 0; i < totalExpenses.size(); i++) {
                        expenseChartData.add(new PieChart.Data(array[i] + " $" + totalExpenses.get(array[i]), totalExpenses.get(array[i])));
                    }
                    expenseChart = new PieChart(expenseChartData);
                    expenseChart.setClockwise(true);
                    expenseChart.setLabelLineLength(75);
                    expenseChart.setLabelsVisible(true);
                    expenseChart.setStartAngle(180);
                    borderPane.setCenter(expenseChart);
                }
        }
    }
    public void updatePieChart() {
        if (addAll.isSelected()) {
            ObservableList<PieChart.Data> expenseChartData = FXCollections.observableArrayList();
            for(int i = 0; i < expenseTypes.length; i++) {
                if(totalExpenses.containsKey(expenseTypes[i])) {
                    expenseChartData.add(new PieChart.Data(expenseTypes[i], totalExpenses.get(expenseTypes[i])));
                }
            }
            expenseChart = new PieChart(expenseChartData);
            expenseChart.setClockwise(true);
            expenseChart.setLabelLineLength(75);
            expenseChart.setLabelsVisible(true);
            expenseChart.setStartAngle(180);
            borderPane.setCenter(expenseChart);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scanner activeUserSheet = null;
        Scanner scanner = null;
        try {
            activeUserSheet = new Scanner(new File("src/DataFiles/ActiveUser.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String user = activeUserSheet.nextLine();
        try {
            scanner = new Scanner(new File("src/DataFiles/UserFiles/" + user + ".txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (scanner.hasNext()) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] expenseConstruct = line.split(",");
                Expense expense = new Expense(Double.parseDouble(expenseConstruct[0]), expenseConstruct[1], expenseConstruct[2]);
                queue.add(expense);
            }
            title.setText("Hello " + user);
            for (int i = 0; i < queue.size(); i++) {
                expenseTree.add(queue.get(i));
                if (!totalExpenses.containsKey(queue.get(i).getExpenseType())) {
                    totalExpenses.put(queue.get(i).getExpenseType(), queue.get(i).getTotal());
                } else {
                    double newAmount = totalExpenses.get(queue.get(i).getExpenseType()) + queue.get(i).getTotal();
                    totalExpenses.replace(queue.get(i).getExpenseType(), newAmount);
                }
            }
            updatePieChart();
        }
    }
}
