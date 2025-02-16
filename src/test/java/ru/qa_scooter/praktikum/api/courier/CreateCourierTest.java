package ru.qa_scooter.praktikum.api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.CourierPojo;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class CreateCourierTest extends BaseCourierTest {

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем создается ли новый курьер при заполнении всех обязательных полей")
    public void testCreateCourierAndCheckResponse() {

        //Тело запроса с данными аккаунта
        courier = new CourierPojo(login,password,firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest(COURIER_HANDLE, courier);
        //Проверяем код ответа
        verifyStatusCode(response,SC_CREATED);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "ok", true);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверяем возвращается ли ошибка при повторном создании курьера используя те же самые данные")
    public void testCreateIdenticalCourierAndCheckResponse() {

        //Тело запроса с данными аккаунта
        courier = new CourierPojo(login,password,firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest(COURIER_HANDLE, courier);
        //Отправляем повторный POST запрос на создание аккаунта с идентичными данными
        response = sendPostRequest(COURIER_HANDLE, courier);
        //Проверяем код ответа, что аккаунт не был создан
        verifyStatusCode(response,SC_CONFLICT);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Проверяем возвращается ли ошибка при создании курьера без обязательного поля логина")
    public void testCreateCourierMissingLogin() {

        //Тело запроса без логина
        courier = new CourierPojo(null,password,firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest(COURIER_HANDLE, courier);
        //Проверяем код ответа, что аккаунт не был создан
        verifyStatusCode(response,SC_BAD_REQUEST);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Проверяем возвращается ли ошибка при создании курьера без обязательного поля пароля")
    public void testCreateCourierMissingPassword() {

        //Тело запроса без пароля
        courier = new CourierPojo(login,null,firstName);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest(COURIER_HANDLE, courier);
        //Проверяем код ответа, что аккаунт не был создан
        verifyStatusCode(response,SC_BAD_REQUEST);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без имени")
    @Description("Проверяем создается ли курьер без необязательного поля имени")
    public void testCreateCourierMissingFirstName() {

        //Тело запроса без пароля
        courier = new CourierPojo(login, password,null);
        //Отправляем POST запрос на создание аккаунта
        response = sendPostRequest(COURIER_HANDLE, courier);
        //Проверяем что аккаунт был создан
        verifyStatusCode(response,SC_CREATED);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "ok", true);
    }

    @After
    public void deleteCreatedAccount() {
        if(response.getStatusCode() == SC_CREATED) {
            int id = sendPostRequest(COURIER_LOGIN_HANDLE, courier).jsonPath().getInt("id");
            sendDeleteRequest("/api/v1/courier/", id);
        }
    }
}