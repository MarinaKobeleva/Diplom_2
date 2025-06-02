import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetOrder extends BaseSpecClass {

    public GetOrder() {

    }

    public Response getOrderWithoutAuto() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(UriConst.ORDER_URI);
        return response;
    }

    public Response getOrderWithAuto(String accessToken) {
        Response response = given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .when()
                .get(UriConst.ORDER_URI);
        return response;
    }
}
