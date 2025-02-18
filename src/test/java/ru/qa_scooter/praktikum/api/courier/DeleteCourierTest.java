package ru.qa_scooter.praktikum.api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.CourierPojo;
import org.junit.Test;

public class DeleteCourierTest extends BaseCourierTest{

    @Test
    @DisplayName("Удаление существующего аккаунта")
    @Description("Попытка удалить существующий аккаунт через его id")
    public void testDeleteCourierAccount(){

        //Тело запроса на создание аккаунта
        courier = new CourierPojo(login,password,null);
        //Отправляем POST запрос на создание аккаунта
        sendPostRequest(COURIER_HANDLE, courier);
        //Отправляем POST запрос на авторизацию и получаем id
        int id = sendPostRequest(COURIER_LOGIN_HANDLE, courier).jsonPath().getInt("id");
        //Отправляем DELETE запрос
        response = sendDeleteRequest(COURIER_DELETE_HANDLE, id);
        //Проверяем код ответа
        verifyStatusCode(response,200);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "ok", true);
    }

    @Test
    @DisplayName("Удаление не передавая id")
    @Description("Попытка отправить DELETE запрос без id")
    public void testDeleteCourierNoId(){

        //Отправляем DELETE запрос без id
        response = sendDeleteRequest(COURIER_DELETE_HANDLE);
        //Проверяем код ответа
        verifyStatusCode(response,400);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Недостаточно данных для удаления курьера");
    }

    @Test
    @DisplayName("Удаление несуществующего аккаунта")
    @Description("Попытка удалить несуществующий аккаунт через id")
    public void testDeleteCourierWrongId(){

        int id = 999999;
        //Отправляем DELETE запрос
        response = sendDeleteRequest(COURIER_DELETE_HANDLE, id);
        //Проверяем код ответа
        verifyStatusCode(response,404);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Курьера с таким id нет.");
    }
}
