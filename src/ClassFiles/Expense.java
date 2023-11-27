package ClassFiles;

public class Expense {
    private double total;
    private String expenseName;
    private String expenseType;

    public Expense(double total, String expenseName, String expenseType) {
        this.total = total;
        this.expenseName = expenseName;
        this.expenseType = expenseType;
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
    public String getExpenseType() {
        return expenseType;
    }
    public void setExpenseType(String expenseLocation) {
        this.expenseType = expenseLocation;
    }
}
