import io.restassured.response.Response;
import pojo.User;

import static io.restassured.RestAssured.given;

public class LoginUser extends BaseSpecClass {
    private final User user;

    public LoginUser(User user) {
        this.user = user;
    }

    public Response loginUser() {
        Response response = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post(UriConst.LOGIN_URI);
        return response;
    }
}
