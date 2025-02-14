package ru.qa_scooter.praktikum.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BaseApiTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Отправляем GET-запрос на {endpoint}")
    public Response sendGetRequest(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("Отправляем POST запрос на {endpoint}")
    public Response sendPostRequest(String endpoint, Object jsonBody){
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }


    @Step("Отправляем DELETE запрос на {endpoint}{id}")
    public Response sendDeleteRequest(String endpoint, int id){
        return given()
                .when()
                .delete(endpoint + id)
                .then()
                .extract()
                .response();
    }

    @Step("Отправляем DELETE запрос на {endpoint} без id")
    public Response sendDeleteRequest(String endpoint){
        return given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("Отправляем PUT запрос на {endpoint} с одним параметром {queryKey}:{queryValue}")
    public Response sendPutRequestOneParam(String endpoint, String queryKey, String queryValue){
        return given()
                .queryParam(queryKey,queryValue)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    @Step("Проверяем, что код ответа равен {expectedStatusCode}")
    public void verifyStatusCode(Response response, int expectedStatusCode) {
        response.then()
                .statusCode(expectedStatusCode);
    }

    @Step("Проверяем, что {jsonPath} имеет значение {expectedValue}")
    public void verifyResponseBodyParameter(Response response, String jsonPath,Object expectedValue){
        response.then()
                .body(jsonPath, equalTo(expectedValue));
    }

    @Step("Проверяем, что ключ {jsonPath} существует и его тип int")
    public void verifyResponseBodyInt(Response response, String jsonPath){
        response.then()
                .body("$", hasKey(jsonPath))
                .body(jsonPath, instanceOf(Integer.class));
    }
}