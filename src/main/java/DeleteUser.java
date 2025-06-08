import pojo.User;

import static io.restassured.RestAssured.given;

public class DeleteUser extends BaseSpecClass {

    public DeleteUser() {
    }

    public void deleteUser(String accessToken) {
        given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .delete(UriConst.USER_URI);
    }
}
