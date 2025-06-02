package pojo;

public class UserWithoutEmailAndPassword {
    private String name;

    public UserWithoutEmailAndPassword(String name) {
        this.name = name;
    }

    public UserWithoutEmailAndPassword() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
