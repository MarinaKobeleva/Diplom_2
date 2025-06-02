import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Order;
import pojo.User;
import pojo.UserRegisterResponse;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest {

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

    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuto() {
        List<String> ingredients = Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f");
        Order order = new Order(ingredients);
        CreateOrder createOrder = new CreateOrder(order);
        return createOrder.createOrderWithAuto(accessToken);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuto() {
        List<String> ingredients = Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f");
        Order order = new Order(ingredients);
        CreateOrder createOrder = new CreateOrder(order);
        return createOrder.createOrder();
    }

    @Step("Создание заказа с ингредиентами")
    public Response createOrderWithIngredients() {
        List<String> ingredients = Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa6e");
        Order order = new Order(ingredients);
        CreateOrder createOrder = new CreateOrder(order);
        return createOrder.createOrder();
    }

    @Step("Создание заказа без ингредиентов")
    public Response createOrderWithoutIngredients() {
        List<String> ingredients = Arrays.asList();
        Order order = new Order(ingredients);
        CreateOrder createOrder = new CreateOrder(order);
        return createOrder.createOrder();
    }

    @Step("Создание заказа с неверным хешем ингредиентов")
    public Response createOrderWithInvalidHashIngredients() {
        List<String> ingredients = Arrays.asList(
                "61c0c5a71d1f820010000000",
                "61c0c5a71d1f820010000000");
        Order order = new Order(ingredients);
        CreateOrder createOrder = new CreateOrder(order);
        return createOrder.createOrder();
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
    @DisplayName("Create order with auto and check status code 200")
    @Description("Создание заказа с авторизацией. Проверить код ответа")
    public void createOrderWithAutoAndCheckStatusCode200() {
        Response response = createOrderWithAuto();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Create order with auto and check response body")
    @Description("Создание заказа с авторизацией. Проверить тело ответа")
    public void createOrderWithAutoAndCheckResponseBody() {
        Response response = createOrderWithAuto();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Create order without auto and check status code 200")
    @Description("Создание заказа без авторизации. Проверить код ответа")
    public void createOrderWithoutAutoAndCheckStatusCode200() {
        Response response = createOrderWithoutAuto();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Create order without auto and check response body")
    @Description("Создание заказа без авторизации. Проверить тело ответа")
    public void createOrderWithoutAutoAndCheckResponseBody() {
        Response response = createOrderWithoutAuto();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Create order with ingredients and check status code 200")
    @Description("Создание заказа с ингредиентами. Проверить код ответа")
    public void createOrderWithIngredientsAndCheckStatusCode200() {
        Response response = createOrderWithIngredients();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Create order with ingredients and check response body")
    @Description("Создание заказа с ингредиентами. Проверить тело ответа")
    public void createOrderWithIngredientsAndCheckResponseBody() {
        Response response = createOrderWithIngredients();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Create order without ingredients and check status code 400")
    @Description("Создание заказа без ингредиентов. Проверить код ответа")
    public void createOrderWithoutIngredientsAndCheckStatusCode400() {
        Response response = createOrderWithoutIngredients();
        checkResponseCode(response, 400);
    }

    @Test
    @DisplayName("Create order without ingredients and check response body")
    @Description("Создание заказа без ингредиентов. Проверить тело ответа")
    public void createOrderWithoutIngredientsAndCheckResponseBody() {
        Response response = createOrderWithoutIngredients();
        checkResponseBodyWithBoolean(response, "success", false);
    }

    @Test
    @DisplayName("Create order with invalid hash ingredients and check status code 500")
    @Description("Создание заказа с неверным хешем ингредиентов. Проверить код ответа")
    public void createOrderWithInvalidHashIngredientsAndCheckStatusCode500() {
        Response response = createOrderWithInvalidHashIngredients();
        checkResponseCode(response, 400);
    }

    @Test
    @DisplayName("Create order with invalid hash ingredients and check response body")
    @Description("Создание заказа с неверным хешем ингредиентов. Проверить тело ответа")
    public void createOrderWithInvalidHashIngredientsAndCheckResponseBody() {
        Response response = createOrderWithInvalidHashIngredients();
        checkResponseBodyWithBoolean(response, "success", false);
    }
}
