import io.restassured.response.Response;
import pojo.UserWithoutEmail;

import static io.restassured.RestAssured.given;

public class ChangeUserPassword extends BaseSpecClass {

    private final UserWithoutEmail userWithoutEmail;

    public ChangeUserPassword(UserWithoutEmail userWithoutEmail) {
        this.userWithoutEmail = userWithoutEmail;
    }

    public Response ChangeUserWithoutEmail(String accessToken) {
        Response response = given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .body(userWithoutEmail)
                .when()
                .patch(UriConst.USER_URI);
        return response;
    }

    public Response ChangeUserWithoutEmailWithoutAuto() {
        Response response = given()
                .spec(requestSpec)
                .body(userWithoutEmail)
                .when()
                .patch(UriConst.USER_URI);
        return response;
    }
}
