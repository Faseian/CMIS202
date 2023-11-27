package ClassFiles;

import java.util.ArrayList;

public class User {
    private String username;
    private String pass;
    private ArrayList<Expense> expenses = new ArrayList<>();

    public User(String username, String pass, ArrayList<Expense> expenses) {
        this.username = username;
        this.pass = pass;
        this.expenses = expenses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }
    public void addExpense(ArrayList<Expense> expenses, Expense expense) {
        expenses.add(expense);
    }
    public void addAll(String username, String pass, Expense expenses) {
        this.username = username;
        this.pass = pass;
        this.expenses.add(expenses);
    }
}
