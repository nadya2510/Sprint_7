import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;

//Courier - Создание курьера
public class CourierCreateTest extends ApiScooter {

    String json;
    String jsonLogin;
    Courier courier = new Courier();

    @Before
    public void setUp() {
        RestAssured.baseURI = urlApi;
        json = "{\"login\": \"loginTest123\", \"password\": \"1234\", \"firstName\": \"firstName\"}";
        jsonLogin = "{\"login\": \"loginTest123\", \"password\": \"1234\"}";
    }
    //курьера можно создать, чтобы создать курьера, нужно передать в ручку все обязательные поля- код
    @Test
    @DisplayName("201-Check create courier status code of /api/v1/courier")
    @Description("Status code 201 test for api/v1/courier")
    public void checkCreatedCourierCode() {
        postRequestCourier(json).then().statusCode(201);
    }

    //курьера можно создать, чтобы создать курьера, нужно передать в ручку все обязательные поля- текст
    @Test
    @DisplayName("201-Check create courier body text of /api/v1/courier")
    @Description("Body text 201 test for api/v1/courier")
    public void checkCreatedCourierBodyText() {
        String textBody = postRequestCourier(json).body().asString();
        String expectedText = "{\"ok\":true}";
        MatcherAssert.assertThat(textBody, equalTo(expectedText));
    }

    //если создать пользователя с логином (пароли разные), который уже есть, возвращается ошибка- код
    @Test
    @DisplayName("409-Check create duplicate login status code of /api/v1/courier")
    @Description("Status code 409 duplicate login 409 test for api/v1/courier")
    public void checkCreatedDuplicateLoginCode() {
        postRequestCourier(json);
        json = "{\"login\": \"loginTest123\", \"password\": \"12345\", \"firstName\": \"firstName2\"}";
        postRequestCourier(json).then().statusCode(409);
    }

    //если создать пользователя с логином (пароли разные), который уже есть, возвращается ошибка- текст
    @Test
    @DisplayName("409-Check create duplicate login text of  /api/v1/courier")
    @Description("Text 409 duplicate login 409 test for api/v1/courier")
    public void checkCreatedDuplicateLoginText() {
        postRequestCourier(json);
        json = "{\"login\": \"loginTest123\", \"password\": \"12345\", \"firstName\": \"firstName2\"}";
        postRequestCourier(json).then().assertThat().body("message",equalTo("Этот логин уже используется"));
    }

    //нельзя создать двух одинаковых курьеров- код
    @Test
    @DisplayName("409-Check create duplicate courier status code of /api/v1/courier")
    @Description("Status code 409 duplicate courier 409 test for api/v1/courier")
    public void checkCreatedDuplicateCourierCode() {
        postRequestCourier(json);
        postRequestCourier(json).then().statusCode(409);
    }

    //нельзя создать двух одинаковых курьеров - текст
    @Test
    @DisplayName("409-Check create duplicate courier text of /api/v1/courier")
    @Description("Text create duplicate courier  409 test for api/v1/courier")
    public void checkCreatedDuplicateCourierText() {
        postRequestCourier(json);
        postRequestCourier(json).then().assertThat().body("message",equalTo("Этот логин уже используется"));
    }

    //если одного из полей (FirstName) нет, запрос возвращает ошибку - код
    @Test
    @DisplayName("400-Check create courier firstName absent status code of /api/v1/courier")
    @Description("Status code 400 firstName absent for api/v1/courier")
    public void checkCreatedCourierFirstNameAbsentCode() {
        postRequestCourier(jsonLogin).then().statusCode(400);
    }

    //если одного из полей (FirstName) нет, запрос возвращает ошибку - текст
    @Test
    @DisplayName("400-Check create courier firstName absent text of /api/v1/courier")
    @Description("Text 400 firstName absent for api/v1/courier")
    public void checkCreatedCourierFirstNameAbsentText() {
        postRequestCourier(jsonLogin).then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    //если одного из полей (Password) нет, запрос возвращает ошибку - код
    @Test
    @DisplayName("400-Check create courier password absent status code of /api/v1/courier")
    @Description("Status cod 400 password absent for api/v1/courier")
    public void checkCreatedCourierPasswordAbsentCode() {
        json = "{\"login\": \"loginTest123\", \"firstName\": \"firstName\"}";
        jsonLogin = "{\"login\": \"loginTest123\", \"password\": \"\"}";
        postRequestCourier(json).then().statusCode(400);
    }

    //если одного из полей (Password) нет, запрос возвращает ошибку - текст
    @Test
    @DisplayName("400-Check create courier password absent text of /api/v1/courier")
    @Description("Text 400 password absent for api/v1/courier")
    public void checkCreatedCourierPasswordAbsentText() {
        json = "{\"login\": \"loginTest123\", \"firstName\": \"firstName\"}";
        jsonLogin = "{\"login\": \"loginTest123\", \"password\": \"\"}";
        postRequestCourier(json).then().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    //если одного из полей (login) нет, запрос возвращает ошибку - код
    @Test
    @DisplayName("400-Check create courier login absent status code of /api/v1/courier")
    @Description("Status cod 400 login absent for api/v1/courier")
    public void checkCreatedCourierLoginAbsentCode() {
        json = "{\"password\": \"1234\", \"firstName\": \"firstName\"}";
        jsonLogin = "{\"login\": \"\", \"password\": \"1234\"}";
        postRequestCourier(json).then().statusCode(400);
    }
    //если одного из полей (login) нет, запрос возвращает ошибку - текст
    @Test
    @DisplayName("400-Check create courier login absent text of /api/v1/courier")
    @Description("Text 400 login absent text for api/v1/courier")
    public void checkCreatedCourierLoginAbsentText() {
        json = "{\"password\": \"1234\", \"firstName\": \"firstName\"}";
        jsonLogin = "{\"login\": \"\", \"password\": \"1234\"}";
        postRequestCourier(json).then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }


    @After
    public void deleteCourier() {
        Integer id = loginCourier(jsonLogin);
        deleteCourier(id);
    }

    // Создаем курьера
    @Step("Send Post request to /api/v1/courier")
    public Response postRequestCourier(String bodyJason) {
        Response response = courier.addCourier(bodyJason);
        return response;
    }

    // Получаем id курьера
    @Step("Send Post request to /api/v1/courier/login")
    public Integer loginCourier(String bodyJason) {
        Integer id = courier.loginIdCourier(bodyJason);
        return id;
    }

    // Удаляем курьера
    @Step("Send Delete request to /api/v1/..")
    public void deleteCourier(Integer id) {
        courier.deleteCourier(id);
    }
}


