package ru.qa_scooter.praktikum.api.courier;

import io.restassured.response.Response;
import org.junit.Before;
import ru.qa_scooter.praktikum.api.BaseApiTest;
import java.util.Random;

public class BaseCourierTest extends BaseApiTest {

    protected String login;
    protected String password;
    protected String firstName;
    protected String jsonBody;
    protected Response response;

    @Before
    public void generateCourierData(){
        //Генерируем данные для создания и вхождения в аккаунт
        login = "barnie" + new Random().nextInt(9999);
        password = "" + new Random().nextInt(9999);
        firstName = "myles" + new Random().nextInt(9999);
    }
}
