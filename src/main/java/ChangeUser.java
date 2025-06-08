import io.restassured.response.Response;
import pojo.User;

import static io.restassured.RestAssured.given;

public class ChangeUser extends BaseSpecClass {
    private final User user;

    public ChangeUser(User user) {
        this.user = user;
    }

    public Response changeUser(String accessToken) {
        Response response = given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(UriConst.USER_URI);
        return response;
    }

    public Response changeUserWithoutAuto() {
        Response response = given()
                .spec(requestSpec)
                .body(user)
                .when()
                .patch(UriConst.USER_URI);
        return response;
    }
}
