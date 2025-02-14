package ru.qa_scooter.praktikum.api.orders;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.OrderListPojo;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class OrderListTest extends BaseOrderTest {

    @Test
    @DisplayName("Проверка списка заказов без id курьера.")
    @Description("Проверяем, что в тело ответа возвращается общий список заказов.")
    public void testOrderListResponseBody(){
        //Отправляем GET запрос для получения списка заказов
        response = sendGetRequest(ORDERS_HANDLE);
        //Проверяем код ответа
        verifyStatusCode(response,200);
        // Десериализуем JSON
        OrderListPojo orderListResponse = response.as(OrderListPojo.class);
        //Проверяем тело ответа на наличие списка заказов
        testOrdersExist(orderListResponse);
        //Проверяем что список заказов не пустой
        testOrdersNotEmpty(orderListResponse);
    }

    @Step("Проверяем что поле Orders существует")
    public void testOrdersExist(OrderListPojo orderListResponse){
        assertNotNull("Нету поля 'orders'", orderListResponse.getOrders());
    }

    @Step("Проверяем что поле Orders заполнено")
    public void testOrdersNotEmpty(OrderListPojo orderListResponse){
        assertFalse("Пустое поле 'orders'", orderListResponse.getOrders().isEmpty());
    }
}