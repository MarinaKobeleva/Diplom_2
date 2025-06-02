import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import pojo.UserRegisterResponse;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderTest {

    private String accessToken;

    @Before
    public void setUp() {
        User user = new User("newTestEmail@yandex.ru", "newTestPassword", "newTestUsername");
        RegisterUser registerUser = new RegisterUser(user);
        Response response = registerUser.registerUser();
        accessToken = response.as(UserRegisterResponse.class).getAccessToken();
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            DeleteUser deleteUser = new DeleteUser();
            deleteUser.deleteUser(accessToken);
        }
    }

    @Step("Получение заказов пользователя с авторизацией")
    public Response getOrderUserWithAuto() {
        GetOrder getOrder = new GetOrder();
        return getOrder.getOrderWithAuto(accessToken);
    }

    @Step("Получение заказов пользователя без авторизации")
    public Response getOrderUserWithoutAuto() {
        GetOrder getOrder = new GetOrder();
        return getOrder.getOrderWithoutAuto();
    }

    @Step("Проверить тело ответа с boolean")
    public void checkResponseBodyWithBoolean(Response response, String key, boolean value) {
        response.then().body(key, equalTo(value));
    }

    @Step("Проверить код ответа")
    public void checkResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }

    @Test
    @DisplayName("Get order user with auto and check status code 200")
    @Description("Получение заказов пользователя с авторизацией. Проверить код ответа")
    public void getOrderUserWithAutoAndCheckStatusCode200() {
        Response response = getOrderUserWithAuto();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Get order user with auto and check response body")
    @Description("Получение заказов пользователя с авторизацией. Проверить тело ответа")
    public void getOrderUserWithAutoAndCheckResponseBody() {
        Response response = getOrderUserWithAuto();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Get order user without auto and check status code 401")
    @Description("Получение заказов пользователя без авторизации. Проверить код ответа")
    public void getOrderUserWithoutAutoAndCheckStatusCode401() {
        Response response = getOrderUserWithoutAuto();
        checkResponseCode(response, 401);
    }

    @Test
    @DisplayName("Get order user without auto and check response body")
    @Description("Получение заказов пользователя без авторизации. Проверить тело ответа")
    public void getOrderUserWithoutAutoAndCheckResponseBody() {
        Response response = getOrderUserWithoutAuto();
        checkResponseBodyWithBoolean(response, "success", false);
    }
}
