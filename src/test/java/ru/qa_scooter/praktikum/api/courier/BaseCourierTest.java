package ru.qa_scooter.praktikum.api.courier;

import io.restassured.response.Response;
import models.CourierPojo;
import org.junit.Before;
import ru.qa_scooter.praktikum.api.BaseApiTest;
import java.util.Random;

public class BaseCourierTest extends BaseApiTest {

    protected static final String COURIER_HANDLE = "/api/v1/courier";
    protected static final String COURIER_LOGIN_HANDLE = "/api/v1/courier/login";
    protected static final String COURIER_DELETE_HANDLE = "/api/v1/courier/";

    protected String login;
    protected String password;
    protected String firstName;
    protected CourierPojo courier;
    protected Response response;

    @Before
    public void generateCourierData(){
        //Генерируем данные для создания и вхождения в аккаунт
        login = "barnie" + new Random().nextInt(9999);
        password = "" + new Random().nextInt(9999);
        firstName = "myles" + new Random().nextInt(9999);
    }
}
