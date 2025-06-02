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

public class LoginUserTest {

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

    @Step("Логин под существующим пользователем")
    public Response authorizationWithRequiredFields() {
        User user = new User("newTestEmail@yandex.ru", "newTestPassword");
        LoginUser loginUser = new LoginUser(user);
        return loginUser.loginUser();
    }

    @Step("Логин с неверным логином и паролем")
    public Response authorizationIncorrectLoginAndPassword() {
        User user = new User("IncorrectEmail@yandex.ru", "IncorrectPassword");
        LoginUser loginUser = new LoginUser(user);
        return loginUser.loginUser();
    }

    @Step("Проверить тело ответа с String")
    public void checkResponseBodyWithString(Response response, String key, String value) {
        response.then().body(key, equalTo(value));
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
    @DisplayName("Authorization with required fields and check status code 200")
    @Description("Логин под существующим пользователем. Проверить код ответа")
    public void authorizationWithRequiredFieldsAndCheckStatusCode200() {
        Response response = authorizationWithRequiredFields();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Authorization with required fields and check response body")
    @Description("Логин под существующим пользователем. Проверить тело ответа")
    public void authorizationWithRequiredFieldsAndCheckResponseBody() {
        Response response = authorizationWithRequiredFields();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Authorization with required fields and check status code 401")
    @Description("Логин с неверным логином и паролем. Проверить код ответа")
    public void authorizationIncorrectLoginAndPasswordAndCheckStatusCode401() {
        Response response = authorizationIncorrectLoginAndPassword();
        checkResponseCode(response, 401);
    }

    @Test
    @DisplayName("Authorization with required fields and check response body")
    @Description("Логин с неверным логином и паролем. Проверить тело ответа")
    public void authorizationIncorrectLoginAndPasswordAndCheckResponseBody() {
        Response response = authorizationIncorrectLoginAndPassword();
        checkResponseBodyWithString(response, "message", "email or password are incorrect");
    }



}
