import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import static org.hamcrest.Matchers.equalTo;

//Courier - Удаление курьера
public class CourierDeleteTest extends ApiScooter{
    String json = "{\"login\": \"loginTest123\", \"password\": \"1234\", \"firstName\": \"firstName\"}";
    String jsonLogin = "{\"login\": \"loginTest123\", \"password\": \"1234\"}";
    Courier courier = new Courier();

    @Before
    public void setUp() {
        RestAssured.baseURI= urlApi;
        //Создадим курьера
        postRequestCourier(json);
    }

    //успешный запрос -код;
    @Test
    @DisplayName("200-Check courier delete status code of /api/v1/courier/")
    @Description("Status code 200 courier delete for /api/v1/courier/")
    public void checkCourierDeleteCode() {
        Integer id = loginCourier(jsonLogin);
        deleteCourier(id).then().statusCode(200);
    }

    //успешный запрос -текс;
    @Test
    @DisplayName("200-Check code courier delete text of /api/v1/courier/")
    @Description("Text 200 code courier delete for /api/v1/courier/")
    public void checkCourierDeleteText() {
        Integer id = loginCourier(jsonLogin);
        String textBody = deleteCourier(id).body().asString();
        String expectedText = "{\"ok\":true}";
        MatcherAssert.assertThat(textBody, equalTo(expectedText));

    }

    //если отправить запрос с несуществующим id, вернётся ошибка-код
    @Test
    @DisplayName("404-Check id not found courier delete status code of /api/v1/courier/")
    @Description("Status code 404 id not found courier delete for /api/v1/courier/")
    public void checkCourierDeleteIdNotFoundCode() {
        Integer id = loginCourier(jsonLogin);
        deleteCourier(id);
        deleteCourier(id).then().statusCode(404);
    }

    //если отправить запрос с несуществующим id, вернётся ошибка-текст
    @Test
    @DisplayName("404-Check id not found courier delete text of /api/v1/courier/")
    @Description("Text 404 id not found courier delete for /api/v1/courier/")
    public void checkCourierDeleteIdNotFoundText() {
        Integer id = loginCourier(jsonLogin);
        deleteCourier(id);
        deleteCourier(id).then().assertThat().body("message",equalTo("Курьера с таким id нет"));
    }

    //если отправить запрос без id, вернётся ошибка- код
    @Test
    @DisplayName("400-Check id absent delete status code of /api/v1/courier/")
    @Description("Status cod 400 id absent delete for /api/v1/courier/")
    public void checkCourierDeleteIdAbsentCode() {
        courier.deleteCourier400().then().statusCode(400);
    }

    //если отправить запрос без id, вернётся ошибка-текст
    @Test
    @DisplayName("400-Check id absent delete text of /api/v1/courier/")
    @Description("Text 400 id absent delete for /api/v1/courier/")
    public void checkCourierDeleteIdAbsentText() {
        courier.deleteCourier400().then().assertThat().body("message",equalTo("Недостаточно данных для удаления курьера"));
    }

    // Создаем курьера
    @Step("Send Post request to /api/v1/courier")
    public Response postRequestCourier(String bodyJason) {
        Response response = courier.addCourier(bodyJason);
        return response;
    }

    // Получаем id курьера
    @Step("Send Post request to /api/v1/courier/login")
    public Integer loginCourier(String bodyJason){
        Integer id = courier.loginIdCourier(bodyJason);
        return id;
    }

    // Удаляем курьера
    @Step("Send Delete request to /api/v1/..")
    public Response deleteCourier(Integer id){
        Response response = courier.deleteCourier(id);
        return response;
    }
}
