package ClassFiles;

import java.util.ArrayList;

public class Admin extends User{
    private boolean priv = true;

    public boolean isPriv() {
        return priv;
    }

    public void setPriv(boolean priv) {
        this.priv = priv;
    }

    public Admin(String username, String pass, ArrayList<Expense> expenses) {
        super(username, pass, true, expenses);
    }

}
