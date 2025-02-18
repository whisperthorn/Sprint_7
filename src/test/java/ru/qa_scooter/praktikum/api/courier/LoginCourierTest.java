package ru.qa_scooter.praktikum.api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.CourierPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class LoginCourierTest extends BaseCourierTest{

    @Before
    public void createCourierAccount(){
        courier = new CourierPojo(login,password,null);
        response = sendPostRequest(COURIER_HANDLE, courier);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверяем может ли курьер авторизоваться в существующий аккаунт")
    public void testLoginCourierAndCheckResponse(){

        //Авторизация в аккаунт
        response = sendPostRequest(COURIER_LOGIN_HANDLE, courier);
        //Проверяем код ответа
        verifyStatusCode(response,SC_OK);
        //Проверяем наличие id и его тип в теле ответа
        verifyResponseBodyInt(response,"id");
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Попытка авторизоваться в существующий аккаунт курьера без логина")
    public void testLoginCourierWithoutLogin(){

        CourierPojo courierTest = new CourierPojo(null,password,null);
        //Авторизация в аккаунт
        response = sendPostRequest(COURIER_LOGIN_HANDLE, courierTest);
        //Проверяем код ответа
        verifyStatusCode(response,SC_BAD_REQUEST);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Попытка авторизоваться в существующий аккаунт курьера без пароля")
    public void testLoginCourierWithoutPassword(){

        CourierPojo courierTest = new CourierPojo(login,null,null);
        //Авторизация в аккаунт
        response = sendPostRequest(COURIER_LOGIN_HANDLE, courierTest);
        //Проверяем код ответа
        verifyStatusCode(response,SC_BAD_REQUEST);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    @Description("Попытка авторизоваться в существующий аккаунт используя неверный пароль")
    public void testLoginCourierWithWrongPassword(){

        CourierPojo courierTest = new CourierPojo(login,password+password,null);
        //Авторизация в аккаунт
        response = sendPostRequest(COURIER_LOGIN_HANDLE, courierTest);
        //Проверяем код ответа
        verifyStatusCode(response,SC_NOT_FOUND);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация в несуществующий аккаунт")
    @Description("Попытка авторизоваться по несуществующим данным")
    public void testLoginCourierWithFalseDetails(){

        CourierPojo courierTest = new CourierPojo(login+login,password+password,null);
        //Авторизация в аккаунт
        response = sendPostRequest(COURIER_LOGIN_HANDLE, courierTest);
        //Проверяем код ответа
        verifyStatusCode(response,SC_NOT_FOUND);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response,"message","Учетная запись не найдена");
    }

    @After
    public void deleteCreatedAccount() {
        int id = sendPostRequest(COURIER_LOGIN_HANDLE, courier).jsonPath().getInt("id");
        sendDeleteRequest(COURIER_DELETE_HANDLE, id);
    }
}
