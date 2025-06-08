package pojo;

public class UserWithoutEmail {
    private String password;

    public UserWithoutEmail(String password) {
        this.password = password;
    }

    public UserWithoutEmail() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
