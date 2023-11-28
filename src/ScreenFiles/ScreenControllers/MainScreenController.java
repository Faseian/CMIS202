package ScreenFiles.ScreenControllers;
import ClassFiles.Expense;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
    private LinkedList<Expense> queue = new LinkedList<>();
    private TreeSet<Expense> expenseTree = new TreeSet<>();
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text title;
    @FXML
    private PieChart expenseChart;
    @FXML
    private RadioButton addAll;

    public void addExpensePage (ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../AddExpensePage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updatePieChart(ActionEvent e) {
        if (addAll.isSelected()) {
            ObservableList<PieChart.Data> expenseChartData = FXCollections.observableArrayList();
            for(int i = 0; i < queue.size() - 1; i++) {
                expenseChartData.add(new PieChart.Data(queue.get(i).getExpenseType(), queue.get(i).getTotal()));
                expenseChart = new PieChart(expenseChartData);
                expenseChart.setTitle("Expense Chart");
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
            for(int i = 0; i < queue.size() - 1; i++) {
                expenseChartData.add(new PieChart.Data(queue.get(i).getExpenseType(), queue.get(i).getTotal()));
                expenseChart = new PieChart(expenseChartData);
                expenseChart.setTitle("Expense Chart");
                expenseChart.setClockwise(true);
                expenseChart.setLabelLineLength(75);
                expenseChart.setLabelsVisible(true);
                expenseChart.setStartAngle(180);
                borderPane.setCenter(expenseChart);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(expenseChart);
        Scanner activeUserSheet = null;
        Scanner scanner = null;
        try {
            activeUserSheet = new Scanner(new File("src/DataFiles/ActiveUser.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String lineHolder = activeUserSheet.nextLine();
        int placeHolder = lineHolder.indexOf(",");
        String user = lineHolder.substring(0,placeHolder);
        try {
            scanner = new Scanner(new File("src/DataFiles/UserFiles/" + user + ".txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] expenseConstruct = line.split(",");
            Expense expense = new Expense(Double.parseDouble(expenseConstruct[0]), expenseConstruct[1], expenseConstruct[2]);
            queue.add(expense);
        }
        title.setText("Hello " + user);
        updatePieChart();
    }
}
