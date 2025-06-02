import io.restassured.response.Response;
import pojo.User;
import pojo.UserRegisterResponse;

import static io.restassured.RestAssured.given;

public class RegisterUser extends BaseSpecClass {
    private final User user;

    public RegisterUser(User user) {
        this.user = user;
    }

    public Response registerUser() {
        Response response = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post(UriConst.REGISTER_URI);
        return response;
    }
}
