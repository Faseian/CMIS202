package ClassFiles;

public class Expense {
    private double total;
    private String expenseName;
    private String expenseLocation;

    public Expense(double total, String expenseName, String expenseLocation) {
        this.total = total;
        this.expenseName = expenseName;
        this.expenseLocation = expenseLocation;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseLocation() {
        return expenseLocation;
    }

    public void setExpenseLocation(String expenseLocation) {
        this.expenseLocation = expenseLocation;
    }
}
