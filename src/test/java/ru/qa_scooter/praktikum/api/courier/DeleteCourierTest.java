package ru.qa_scooter.praktikum.api.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.Random;

public class DeleteCourierTest extends BaseCourierTest{

    @Test
    @DisplayName("Удаление существующего аккаунта")
    @Description("Попытка удалить существующий аккаунт через его id")
    public void testDeleteCourierAccount(){

        //Тело запроса на создание аккаунта
        jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);
        //Отправляем POST запрос на создание аккаунта
        sendPostRequest("/api/v1/courier", jsonBody);
        //Отправляем POST запрос на авторизацию и получаем id
        int id = sendPostRequest("/api/v1/courier/login", jsonBody).jsonPath().getInt("id");
        //Отправляем DELETE запрос
        response = sendDeleteRequest("/api/v1/courier/", id);
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
        response = sendDeleteRequest("/api/v1/courier/");
        //Проверяем код ответа
        verifyStatusCode(response,400);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Недостаточно данных для удаления курьера");
    }

    @Test
    @DisplayName("Удаление несуществующего аккаунта")
    @Description("Попытка удалить несуществующий аккаунт через id")
    public void testDeleteCourierWrongId(){

        //Создаем случайный id
        int id = 999999;
        //Отправляем DELETE запрос
        response = sendDeleteRequest("/api/v1/courier/", id);
        //Проверяем код ответа
        verifyStatusCode(response,404);
        //Проверяем тело ответа
        verifyResponseBodyParameter(response, "message", "Курьера с таким id нет.");
    }
}
