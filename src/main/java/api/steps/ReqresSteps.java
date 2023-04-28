package api.steps;

import api.models.CreateUserPayload;
import api.models.ListUsersResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class ReqresSteps {

    @Step("Отправить запрос GET reqres.in/api/users")
    public static ListUsersResponse getUsersSuccess() {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .get("users?page=2")
                .then()
                .spec(SpecHelper.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", ListUsersResponse.class);
    }

    @Step("Отправить запрос GET reqres.in/api/users с параметром page = {0}")
    public static ListUsersResponse getUsersSuccess(String page) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .get(String.format("users?page=%s", page) )
                .then()
                .spec(SpecHelper.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", ListUsersResponse.class);
    }

    @Step("Отправить запрос POST reqres.in/api/users")
    public static Response postUsersSuccess(CreateUserPayload payload) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .body(payload)
                .post("users")
                .then()
                .spec(SpecHelper.getResponseSpec(201))
                .extract()
                .response();
    }

    @Step("Проверка окончания адреса электронной почты")
    public void checkEMail(ListUsersResponse response) {
        Assertions.assertTrue(response.getData().stream().allMatch(x -> x.getEMail().endsWith("@reqres.in")));
    }

    @Step("Проверка значений полей name и job в ответе")
    public void checkCreateUser(Response response, CreateUserPayload payload) {
        Assertions.assertEquals(payload.getName(), response.jsonPath().get("name"));
        Assertions.assertEquals(payload.getJob(), response.jsonPath().get("job"));
    }
}
