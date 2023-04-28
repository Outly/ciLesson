package repres;

import api.models.CreateUserPayload;
import api.models.ListUsersResponse;
import api.models.User;
import api.steps.ReqresSteps;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Api tests")
@Feature("Reqres service")
@Story("Методы для работы с пользователем")
@Link(name = "Документация сервиса", url = "https://reqres.in/")
@Owner("Долженко Артём")
public class TestReqres {

    private final static String URL = "https://reqres.in/api/";

    private final ReqresSteps reqresSteps = new ReqresSteps();

//    @Test
//    public void successCheckAvatar() {
//        List<User> users = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .get(URL + "users?page=2")
//                .then()
//                .log().all()
//                .extract().body().jsonPath().getList("data", User.class);
//
//        users.stream().forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
//    }
//
//    @Test
//    public void successCheckEMail() {
//        ListUsersResponse response = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .get(URL + "users?page=2")
//                .then()
//                .log().all()
//                .extract().body().jsonPath().getObject(".", ListUsersResponse.class);
//
//        Assertions.assertTrue(response.getData().stream().allMatch(x -> x.getEMail().endsWith("@reqres.in")));
//    }

    @Test
    @Description("Проеряем, что на странице 2 почта пользотелей оканчивается на @reqres.in")
    @DisplayName("Проверка доменного имени почты")
    @Severity(SeverityLevel.NORMAL)
    public void successCheckEMail2() {
        ListUsersResponse response = reqresSteps.getUsersSuccess();

        reqresSteps.checkEMail(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    @Description("Проеряем, что почта пользотелей оканчивается на @reqres.in")
    @DisplayName("Проверка доменного имени почты")
    public void successCheckEMail3(String page) {
        ListUsersResponse response = reqresSteps.getUsersSuccess(page);

        reqresSteps.checkEMail(response);
    }

    @Test
    @Description("Проверяем, что предаваемые в запросе поля name и job совпадают с одноименными полями в ответе")
    @DisplayName("Успешное создание нового пользователя")
    public void successCreateUser() {
        CreateUserPayload payload = new CreateUserPayload("Dolzhenko A", "QA engineer");

        Response response = reqresSteps.postUsersSuccess(payload);

        reqresSteps.checkCreateUser(response, payload);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-data/client-data.csv", numLinesToSkip = 1)
    @Description("Проверяем, что предаваемые в запросе поля name и job совпадают с одноименными полями в ответе")
    @DisplayName("Успешное создание нового пользователя")
    public void successCreateUser2(String name, String job) {
        CreateUserPayload payload = new CreateUserPayload(name, job);

        Response response = reqresSteps.postUsersSuccess(payload);

        reqresSteps.checkCreateUser(response, payload);
    }
}
