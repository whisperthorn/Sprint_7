package ru.qa_scooter.praktikum.api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest extends BaseCourierTest{

    @Before
    public void createCourierAccount(){
        jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);
        response = sendPostRequest("/api/v1/courier", jsonBody);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверяем может ли курьер авторизоваться в существующий аккаунт")
    public void testLoginCourierAndCheckResponse(){

        //Авторизация в аккаунт
        response = sendPostRequest("/api/v1/courier/login", jsonBody);
        //Проверяем код ответа
        verifyStatusCode(response,200);
        //Проверяем наличие id и его тип в теле ответа
        verifyResponseBodyInt(response,"id");
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Попытка авторизоваться в существующий аккаунт курьера без логина")
    public void testLoginCourierWithoutLogin(){

        String jsonBodyTest = String.format("{\"password\": \"%s\"}", password);
        //Авторизация в аккаунт
        response = sendPostRequest("/api/v1/courier/login", jsonBodyTest);
        //Проверяем код ответа
        verifyStatusCode(response,400);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Попытка авторизоваться в существующий аккаунт курьера без пароля")
    public void testLoginCourierWithoutPassword(){

        String jsonBodyTest = String.format("{\"login\": \"%s\"}", login);
        //Авторизация в аккаунт
        response = sendPostRequest("/api/v1/courier/login", jsonBodyTest);
        //Проверяем код ответа
        verifyStatusCode(response,400);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    @Description("Попытка авторизоваться в существующий аккаунт используя неверный пароль")
    public void testLoginCourierWithWrongPassword(){

        String passwordTest = password+password;
        String jsonBodyTest = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, passwordTest);
        //Авторизация в аккаунт
        response = sendPostRequest("/api/v1/courier/login", jsonBodyTest);
        //Проверяем код ответа
        verifyStatusCode(response,404);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация в несуществующий аккаунт")
    @Description("Попытка авторизоваться по несуществующим данным")
    public void testLoginCourierWithFalseDetails(){

        String loginTest = login+login;
        String passwordTest = password+password;
        String jsonBodyTest = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", loginTest, passwordTest);
        //Авторизация в аккаунт
        response = sendPostRequest("/api/v1/courier/login", jsonBodyTest);
        //Проверяем код ответа
        verifyStatusCode(response,404);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Учетная запись не найдена");
    }

    @After
    public void deleteCreatedAccount() {
        int id = sendPostRequest("/api/v1/courier/login", jsonBody).jsonPath().getInt("id");
        sendDeleteRequest("/api/v1/courier/", id);
    }
}
