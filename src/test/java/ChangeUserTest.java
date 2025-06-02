import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import pojo.UserRegisterResponse;
import pojo.UserWithoutEmail;
import pojo.UserWithoutEmailAndPassword;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserTest {
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

    @Step("Изменить email с авторизацией")
    public Response changeEmailWithAuto() {
        User user = new User("newTestEmail@yandex.ru");
        ChangeUser changeUser = new ChangeUser(user);
        return changeUser.changeUser(accessToken);
    }

    @Step("Изменить password с авторизацией")
    public Response changePasswordWithAuto() {
        UserWithoutEmail userWithoutEmail = new UserWithoutEmail("newTestPassword");
        ChangeUserPassword changeUserWithoutEmail = new ChangeUserPassword(userWithoutEmail);
        return changeUserWithoutEmail.ChangeUserWithoutEmail(accessToken);
    }

    @Step("Изменить name с авторизацией")
    public Response changeNameWithAuto() {
        UserWithoutEmailAndPassword userWithoutEmailAndPassword = new UserWithoutEmailAndPassword("newTestUsername");
        ChangeUserName changeUserName = new ChangeUserName(userWithoutEmailAndPassword);
        return changeUserName.ChangeUserName(accessToken);
    }

    @Step("Изменить email без авторизации")
    public Response changeEmailWithoutAuto() {
        User user = new User("changeTestEmail@yandex.ru");
        ChangeUser changeUser = new ChangeUser(user);
        return changeUser.changeUserWithoutAuto();
    }

    @Step("Изменить password без авторизации")
    public Response changePasswordWithoutAuto() {
        UserWithoutEmail userWithoutEmail = new UserWithoutEmail("changeTestPassword");
        ChangeUserPassword changeUserWithoutEmail = new ChangeUserPassword(userWithoutEmail);
        return changeUserWithoutEmail.ChangeUserWithoutEmailWithoutAuto();
    }

    @Step("Изменить name без авторизации")
    public Response changeNameWithoutAuto() {
        UserWithoutEmailAndPassword userWithoutEmailAndPassword = new UserWithoutEmailAndPassword("changeTestUsername");
        ChangeUserName changeUserName = new ChangeUserName(userWithoutEmailAndPassword);
        return changeUserName.ChangeUserNameWithoutAuto();
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
    @DisplayName("Change Email with auto and check status code 200")
    @Description("Изменить email с авторизацией. Проверить код ответа")
    public void changeEmailWithAutoAndCheckStatusCode200() {
        Response response = changeEmailWithAuto();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Change Email with auto and check response body")
    @Description("Изменить email с авторизацией. Проверить тело ответа")
    public void changeEmailWithAutoAndCheckResponseBody() {
        Response response = changeEmailWithAuto();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Change Password with auto and check status code 200")
    @Description("Изменить Password с авторизацией. Проверить код ответа")
    public void changePasswordWithAutoAndCheckStatusCode200() {
        Response response = changePasswordWithAuto();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Change Password with auto and check response body")
    @Description("Изменить Password с авторизацией. Проверить тело ответа")
    public void changePasswordWithAutoAndCheckResponseBody() {
        Response response = changePasswordWithAuto();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Change Name with auto and check status code 200")
    @Description("Изменить Name с авторизацией. Проверить код ответа")
    public void changeNameWithAutoAndCheckStatusCode200() {
        Response response = changeNameWithAuto();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Change Name with auto and check response body")
    @Description("Изменить Name с авторизацией. Проверить тело ответа")
    public void changeNameWithAutoAndCheckResponseBody() {
        Response response = changeNameWithAuto();
        checkResponseBodyWithBoolean(response, "success", true);
    }

    @Test
    @DisplayName("Change Email without auto and check status code 401")
    @Description("Изменить email без авторизации. Проверить код ответа")
    public void changeEmailWithoutAutoAndCheckStatusCode401() {
        Response response = changeEmailWithoutAuto();
        checkResponseCode(response, 401);
    }

    @Test
    @DisplayName("Change Email without auto and check response body")
    @Description("Изменить email без авторизации. Проверить тело ответа")
    public void changeEmailWithoutAutoAndCheckResponseBody() {
        Response response = changeEmailWithoutAuto();
        checkResponseBodyWithString(response, "message", "You should be authorised");
    }

    @Test
    @DisplayName("Change Password without auto and check status code 401")
    @Description("Изменить Password без авторизации. Проверить код ответа")
    public void changePasswordWithoutAutoAndCheckStatusCode401() {
        Response response = changePasswordWithoutAuto();
        checkResponseCode(response, 401);
    }

    @Test
    @DisplayName("Change Password without auto and check response body")
    @Description("Изменить Password без авторизации. Проверить тело ответа")
    public void changePasswordWithoutAutoAndCheckResponseBody() {
        Response response = changePasswordWithoutAuto();
        checkResponseBodyWithString(response, "message", "You should be authorised");
    }

    @Test
    @DisplayName("Change Name without auto and check status code 401")
    @Description("Изменить Name без авторизации. Проверить код ответа")
    public void changeNameWithoutAutoAndCheckStatusCode401() {
        Response response = changeNameWithoutAuto();
        checkResponseCode(response, 401);
    }

    @Test
    @DisplayName("Change Name without auto and check response body")
    @Description("Изменить Name без авторизации. Проверить тело ответа")
    public void changeNameWithoutAutoAndCheckResponseBody() {
        Response response = changeNameWithoutAuto();
        checkResponseBodyWithString(response, "message", "You should be authorised");
    }

}
