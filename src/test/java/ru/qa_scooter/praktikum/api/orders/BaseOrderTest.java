package ru.qa_scooter.praktikum.api.orders;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import ru.qa_scooter.praktikum.api.BaseApiTest;

import static models.TestDataLoader.getFirstTestData;

public class BaseOrderTest extends BaseApiTest {
    protected static final String TEST_DATA_PATH = "src/test/resources/orderTestData.json";
    protected static final String CANCEL_ORDERS_HANDLE = "/api/v1/orders/cancel";
    protected static final String ORDERS_HANDLE = "/api/v1/orders";
    protected Response response;
    protected String trackValue;

    @Before
    public void createNewOrder() throws Exception {
        response = sendPostRequest(ORDERS_HANDLE, getFirstTestData(TEST_DATA_PATH));
        trackValue = String.valueOf(response.jsonPath().getInt("track"));
    }

    @After
    public void cleanUp(){
        response = sendPutRequestOneParam(CANCEL_ORDERS_HANDLE,"track", trackValue);
        verifyStatusCode(response,200);
    }
}