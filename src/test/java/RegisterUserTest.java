import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import pojo.User;
import pojo.UserRegisterResponse;
import pojo.UserWithoutEmail;

import static org.hamcrest.Matchers.equalTo;

public class RegisterUserTest {

    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            DeleteUser deleteUser = new DeleteUser();
            deleteUser.deleteUser(accessToken);
        }
    }


    @Step("Создать уникального пользователя")
    public Response registerUser() {
        User user = new User("newTestEmail@yandex.ru", "newTestPassword", "newTestUsername");
        RegisterUser registerUser = new RegisterUser(user);
        Response response = registerUser.registerUser();
        if (response.as(UserRegisterResponse.class).getAccessToken() != null) {
            accessToken = response.as(UserRegisterResponse.class).getAccessToken();
        }
        return response;
    }

    @Step("Создать пользователя и не заполнить поле email")
    public Response registerUserWithoutEmail() {
        UserWithoutEmail userWithoutEmail = new UserWithoutEmail("newTestPassword");
        RegisterUserWithoutEmail registerUserWithoutEmail = new RegisterUserWithoutEmail(userWithoutEmail);
        return registerUserWithoutEmail.registerUserWithoutEmail();
    }

    @Step("Создать пользователя и не заполнить поле password")
    public Response registerUserWithoutPassword() {
        User user = new User("newTestEmail@yandex.ru");
        RegisterUser registerUser = new RegisterUser(user);
        return registerUser.registerUser();
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
    @DisplayName("Create user and check status code 200")
    @Description("Создать уникального пользователя. Проверить код ответа")
    public void createUserAndCheckStatusCode200() {
        Response response = registerUser();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Create user and check response body")
    @Description("Создать уникального пользователя. Проверить тело ответа")
    public void createUserAndCheckResponseBody() {
        Response response = registerUser();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Create user again and check status code 403")
    @Description("Создать пользователя, который уже зарегистрирован. Проверить код ответа")
    public void createUserAgainAndCheckStatusCode403() {
        Response response1 = registerUser();
        Response response2 = registerUser();
        checkResponseCode(response2, 403);
    }

    @Test
    @DisplayName("Create user again and check response body")
    @Description("Создать пользователя, который уже зарегистрирован. Проверить тело ответа")
    public void createUserAgainAndCheckResponseBody() {
        Response response1 = registerUser();
        Response response2 = registerUser();
        checkResponseBodyWithString(response2, "message", "User already exists");
    }

    @Test
    @DisplayName("Create user without email and check status code 403")
    @Description("Создать пользователя и не заполнить поле email. Проверить код ответа")
    public void createUserWithoutEmailAndCheckStatusCode403() {
        Response response = registerUserWithoutEmail();
        checkResponseCode(response, 403);
    }

    @Test
    @DisplayName("Create user without email and check response body")
    @Description("Создать пользователя и не заполнить поле email. Проверить тело ответа")
    public void createUserWithoutEmailAndCheckResponseBody() {
        Response response = registerUserWithoutEmail();
        checkResponseBodyWithString(response, "message", "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Create user without password and check status code 403")
    @Description("Создать пользователя и не заполнить поле password. Проверить код ответа")
    public void createUserWithoutPasswordAndCheckStatusCode403() {
        Response response = registerUserWithoutPassword();
        checkResponseCode(response, 403);
    }

    @Test
    @DisplayName("Create user without password and check response body")
    @Description("Создать пользователя и не заполнить поле password. Проверить тело ответа")
    public void createUserWithoutPasswordAndCheckResponseBody() {
        Response response = registerUserWithoutPassword();
        checkResponseBodyWithString(response, "message", "Email, password and name are required fields");
    }
}