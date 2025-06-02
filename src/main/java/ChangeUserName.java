import io.restassured.response.Response;
import pojo.UserWithoutEmail;
import pojo.UserWithoutEmailAndPassword;

import static io.restassured.RestAssured.given;

public class ChangeUserName extends BaseSpecClass {
    private final UserWithoutEmailAndPassword userWithoutEmailAndPassword;

    public ChangeUserName(UserWithoutEmailAndPassword userWithoutEmailAndPassword) {
        this.userWithoutEmailAndPassword = userWithoutEmailAndPassword;
    }

    public Response ChangeUserName(String accessToken) {
        Response response = given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .body(userWithoutEmailAndPassword)
                .when()
                .patch(UriConst.USER_URI);
        return response;
    }

    public Response ChangeUserNameWithoutAuto() {
        Response response = given()
                .spec(requestSpec)
                .body(userWithoutEmailAndPassword)
                .when()
                .patch(UriConst.USER_URI);
        return response;
    }
}