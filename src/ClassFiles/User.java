package ClassFiles;

import java.util.ArrayList;

public class User {
    private String username;
    private String pass;
    private ArrayList<Expense> Expenses = new ArrayList<>();

    public User(String username, String pass, ArrayList<Expense> expenses) {
        this.username = username;
        this.pass = pass;
        Expenses = expenses;
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
        return Expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        Expenses = expenses;
    }
    public void addExpense(ArrayList<Expense> expenses, Expense expense) { expenses.add(expense); }
}
