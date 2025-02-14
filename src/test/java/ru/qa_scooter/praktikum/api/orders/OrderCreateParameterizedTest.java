package ru.qa_scooter.praktikum.api.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.OrderCreatePojo;
import models.TestDataLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedTest extends BaseOrderTest {

    private final OrderCreatePojo order;

    public OrderCreateParameterizedTest(OrderCreatePojo order) {
        this.order = order;
    }

    @Override
    @Before
    public void createNewOrder() {
        // Пустое тело, чтобы игнорировать выполнение метода из BaseOrderTest
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() throws Exception {
        return TestDataLoader.getTestData(TEST_DATA_PATH);
    }

    @Test
    @DisplayName("Создаем новый заказ")
    @Description("Проверяем создается ли новый заказ используя валидные тестовые данные и смотрим тело ответа")
    public void testCreateOrderAndCheckResponse(){

        //Отправляем POST запрос на создание заказа
        response = sendPostRequest(ORDERS_HANDLE, order);
        //Проверяем код ответа
        verifyStatusCode(response,201);
        //Проверяем что в теле ответа есть track и тип его значения int
        verifyResponseBodyInt(response,"track");
    }

    @Override
    @After
    public void cleanUp(){
        trackValue = String.valueOf(response.jsonPath().getInt("track"));
        response = sendPutRequestOneParam(CANCEL_ORDERS_HANDLE,"track", trackValue);
        verifyStatusCode(response,200);
    }
}
