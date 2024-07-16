import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest extends ApiScooter{
    String json = "{\"login\": \"loginTest123\", \"password\": \"1234\", \"firstName\": \"firstName\"}";
    String jsonLogin = "{\"login\": \"loginTest123\", \"password\": \"1234\"}";
    Courier courier = new Courier();

    @Before
    public void setUp() {
        RestAssured.baseURI= urlApi;
        //Создадим курьера
        postRequestCourier(json);
    }


    //курьер может авторизоваться;
    //для авторизации нужно передать все обязательные поля- код
    @Test
    @DisplayName("200-Check courier login status code of /api/v1/courier/login")
    @Description("Status code 200 courier login for /api/v1/courier/login")
    public void checkCourierLoginCode() {
        postRequestCourierLogin(jsonLogin).then().statusCode(200);
    }

    //успешный запрос возвращает id
    @Test
    @DisplayName("200-Check code courier text of /api/v1/courier/login")
    @Description("Text 200 code courier for /api/v1/courier/login")
    public void checkCourierLoginText() {
         postRequestCourierLogin(jsonLogin).then().assertThat().body("id", notNullValue());
    }

    //для авторизации нужно передать все обязательные поля (login отсуствует)-код;
    @Test
    @DisplayName("400-Check login absent status code of /api/v1/courier/login")
    @Description("Status code 400 login absent for /api/v1/courier/login")
    public void checkCourierLoginLoginAbsentCode() {
        String jsonTest =  "{ \"password\": \"1234\"}";
        postRequestCourierLogin(jsonTest).then().statusCode(400);
    }

    //для авторизации нужно передать все обязательные поля (login отсуствует)-текст;
    @Test
    @DisplayName("400-Check login absent text of /api/v1/courier/login")
    @Description("Text 400 login absent for /api/v1/courier/login")
    public void checkCourierLoginLoginAbsentText() {
        String jsonTest =  "{ \"password\": \"1234\"}";
        postRequestCourierLogin(jsonTest).then().assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

    //для авторизации нужно передать все обязательные поля (password отсуствует)- код;
    @Test
    @DisplayName("400-Check password absent status code of /api/v1/courier/login")
    @Description("Status code 400 password absent for /api/v1/courier/login")
    public void checkCourierLoginPasswordAbsentCode() {
        String jsonTest =  "{\"login\": \"loginTest123\"}";
        postRequestCourierLogin(jsonTest).then().statusCode(400);
    }

    //для авторизации нужно передать все обязательные поля (password отсуствует)- текст;
    @Test
    @DisplayName("400-Check password absent text of /api/v1/courier/login")
    @Description("Text 400 password absent for /api/v1/courier/login")
    public void checkCourierLoginPasswordAbsentText() {
        String jsonTest =  "{\"login\": \"loginTest123\"}";
        postRequestCourierLogin(jsonTest).then().assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

    //система вернёт ошибку, если неправильно указать логин - код;
    @Test
    @DisplayName("404-Check login error status code of /api/v1/courier/login")
    @Description("Status code 404 login error for /api/v1/courier/login")
    public void checkCourierLoginLoginErrorCode() {
        String jsonTest =  "{\"login\": \"Test1234\", \"password\": \"1234\", \"firstName\": \"firstName\"}";
        postRequestCourierLogin(jsonTest).then().statusCode(404);
    }

    //система вернёт ошибку, если неправильно указать логин - текст;
    @Test
    @DisplayName("404-Check login error text of /api/v1/courier/login")
    @Description("Text 404 login error for /api/v1/courier/login")
    public void checkCourierLoginLoginErrorText() {
        String jsonTest =  "{\"login\": \"Test1234\", \"password\": \"1234\", \"firstName\": \"firstName\"}";
        postRequestCourierLogin(jsonTest).then().assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    //система вернёт ошибку, если неправильно указать пароль - код;
    @Test
    @DisplayName("404-Check password error status code of /api/v1/courier/login")
    @Description("Status code 404 password error for /api/v1/courier/login")
    public void checkCourierLoginPasswordErrorCode() {
        String jsonTest =  "{\"login\": \"Test1234\", \"password\": \"4321\", \"firstName\": \"firstName\"}";
        postRequestCourierLogin(jsonTest).then().statusCode(404);
    }
    //система вернёт ошибку, если неправильно указать пароль - текст;
    @Test
    @DisplayName("404-Check password error text of /api/v1/courier/login")
    @Description("Text 404 password error for /api/v1/courier/login")
    public void checkCourierLoginPasswordErrorText() {
        String jsonTest =  "{\"login\": \"Test1234\", \"password\": \"4321\", \"firstName\": \"firstName\"}";
        postRequestCourierLogin(jsonTest).then().assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку - код;
    @Test
    @DisplayName("404-Check not courier status code of /api/v1/courier/login")
    @Description("Status code 404 not courier for /api/v1/courier/login")
    public void checkCourierLoginNotCourierCode() {
        deleteRequestCourierLogin(jsonLogin);
        postRequestCourierLogin(jsonLogin).then().statusCode(404);
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку - код;
    @Test
    @DisplayName("404-Check not courier text of /api/v1/courier/login")
    @Description("Text 404 not courier for /api/v1/courier/login")
    public void checkCourierLoginNotCourierText() {
        deleteRequestCourierLogin(jsonLogin);
        postRequestCourierLogin(jsonLogin).then().assertThat().body("message",equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier(){
        deleteRequestCourierLogin(jsonLogin);
    }

    // Логин курьера в системе
    @Step("Send Post request to /api/v1/courier/login")
    public Response postRequestCourierLogin(String bodyJason){
        Response response =courier.loginCourier(bodyJason);
        return response;
    }

    // Создаем курьера
    @Step("Send Post request to /api/v1/courier")
    public Response postRequestCourier(String bodyJason) {
        Response response = courier.addCourier(bodyJason);
        return response;
    }

    // Удаление курьера
    @Step("Send delete request to /api/v1/courier/login")
    public void deleteRequestCourierLogin(String bodyJason){
        //Логин курьера в системе ID
        Integer id = courier.loginIdCourier(bodyJason);
        //Удалим
        courier.deleteCourier(id);
    }

}
