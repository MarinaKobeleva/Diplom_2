import io.restassured.response.Response;
import pojo.UserRegisterResponse;
import pojo.UserWithoutEmail;

import static io.restassured.RestAssured.given;

public class RegisterUserWithoutEmail extends BaseSpecClass {
    private final UserWithoutEmail userWithoutEmail;

    public RegisterUserWithoutEmail(UserWithoutEmail userWithoutEmail) {
        this.userWithoutEmail = userWithoutEmail;
    }

    public Response registerUserWithoutEmail() {
        Response response = given()
                .spec(requestSpec)
                .body(userWithoutEmail)
                .when()
                .post(UriConst.REGISTER_URI);
        return response;
    }
}
