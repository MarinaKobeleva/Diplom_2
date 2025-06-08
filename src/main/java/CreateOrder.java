import io.restassured.response.Response;
import pojo.Order;
import pojo.User;

import static io.restassured.RestAssured.given;

public class CreateOrder extends BaseSpecClass {
    private final Order order;

    public CreateOrder(Order order) {
        this.order = order;
    }

    public Response createOrder() {
        Response response = given()
                .spec(requestSpec)
                .body(order)
                .when()
                .post(UriConst.ORDER_URI);
        return response;
    }

    public Response createOrderWithAuto(String accessToken) {
        Response response = given()
                .spec(requestSpec)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(UriConst.ORDER_URI);
        return response;
    }
}
