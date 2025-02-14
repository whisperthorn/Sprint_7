package ru.qa_scooter.praktikum.api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTest extends BaseCourierTest {

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем создается ли новый курьер при заполнении всех обязательных полей")
    public void testCreateCourierAndCheckResponse() {

        //Тело запроса с данными аккаунта
        jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}", login, password, firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest("/api/v1/courier", jsonBody);
        //Проверяем код ответа
        verifyStatusCode(response,201);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "ok", true);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверяем возвращается ли ошибка при повторном создании курьера используя те же самые данные")
    public void testCreateIdenticalCourierAndCheckResponse() {

        //Тело запроса с данными аккаунта
        jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}", login, password, firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest("/api/v1/courier", jsonBody);
        //Проверяем что аккаунт был создан
        verifyStatusCode(response,201);
        //Отправляем повторный POST запрос на создание аккаунта с идентичными данными
        response = sendPostRequest("/api/v1/courier", jsonBody);
        //Проверяем код ответа, что аккаунт не был создан
        verifyStatusCode(response,409);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Проверяем возвращается ли ошибка при создании курьера без обязательного поля логина")
    public void testCreateCourierMissingLogin() {

        //Тело запроса без логина
        jsonBody = String.format("{\"password\": \"%s\", \"firstName\": \"%s\"}", password, firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest("/api/v1/courier", jsonBody);
        //Проверяем код ответа, что аккаунт не был создан
        verifyStatusCode(response,400);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Проверяем возвращается ли ошибка при создании курьера без обязательного поля пароля")
    public void testCreateCourierMissingPassword() {

        //Тело запроса без пароля
        jsonBody = String.format("{\"login\": \"%s\", \"firstName\": \"%s\"}", login, firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest("/api/v1/courier", jsonBody);
        //Проверяем код ответа, что аккаунт не был создан
        verifyStatusCode(response,400);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без имени")
    @Description("Проверяем создается ли курьер без необязательного поля имени")
    public void testCreateCourierMissingFirstName() {

        //Тело запроса без пароля
        jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest("/api/v1/courier", jsonBody);
        //Проверяем что аккаунт был создан
        verifyStatusCode(response,201);
    }

    @After
    public void deleteCreatedAccount() {
        if(response.getStatusCode() == 201) {
            int id = sendPostRequest("/api/v1/courier/login", jsonBody).jsonPath().getInt("id");
            sendDeleteRequest("/api/v1/courier/", id);
        }
    }
}