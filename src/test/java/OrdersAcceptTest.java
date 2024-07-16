import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.hamcrest.MatcherAssert;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;

//Orders - Принять заказ
public class OrdersAcceptTest extends ApiScooter{
    String json;
    String jsonLogin;

    Integer courierId;
    Integer trackId;
    Integer orderId;
    Orders orders = new Orders();
    Courier courier = new Courier();

    @Before
    public void setUp() {
        RestAssured.baseURI = urlApi;
        //Создаем курьера
        json = "{\"login\": \"loginTest123\", \"password\": \"1234\", \"firstName\": \"firstName\"}";
        jsonLogin = "{\"login\": \"loginTest123\", \"password\": \"1234\"}";
        postRequestCourier(json);
        courierId = loginCourier(jsonLogin);
        //Добавим заказ trackId
        OrdersIN json  = new OrdersIN( "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                List.of("BLACK"));
        postRequestOrdersCreate(json);
    }

    //успешный запрос возвращает - код
    @Test
    @DisplayName("200-Check accept order status code of /api/v1/orders/accept/:id")
    @Description("Code test accept order for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptCode() {
        putOrdersAccept(orderId, courierId).then().statusCode(200);
    }

    //успешный запрос возвращает - текст
    @Test
    @DisplayName("200-Check accept order text of /api/v1/orders/accept/:id")
    @Description("Text test accept order for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptText() {
        String textBody = putOrdersAccept(orderId, courierId).body().asString();
        String expectedText = "{\"ok\":true}";
        MatcherAssert.assertThat(textBody, equalTo(expectedText));
    }

    //если не передать id курьера, запрос вернёт ошибку- код
    @Test
    @DisplayName("400-Check accept order courierId absent status code of /api/v1/orders/accept/:id")
    @Description("Status code 400 accept order courierId absent for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptСourierIdAbsentCode() {
        Integer courierId400 = null;
        putOrdersAccept(orderId, courierId400).then().statusCode(400);
    }

    //если не передать id курьера, запрос вернёт ошибку- текст
    @Test
    @DisplayName("400-Check accept order courierId absent text of /api/v1/orders/accept/:id")
    @Description("Text 400 accept order courierId absent for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptСourierIdAbsentText() {
        Integer courierId400 = null;
        putOrdersAccept(orderId, courierId400).then().assertThat().body("message",equalTo("Недостаточно данных для поиска"));

    }

    //если передать неверный id курьера, запрос вернёт ошибку- код
    @Test
    @DisplayName("404-Check not courierId status code of /api/v1/orders/accept/:id")
    @Description("Status code 404 test not courierId  for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptNotCourierIDCode() {
        //удалим курьера
        deleteCourier(courierId);
        putOrdersAccept(orderId, courierId).then().statusCode(404);
    }
    //если не передать id курьера, запрос вернёт ошибку- текст
    @Test
    @DisplayName("404-Check not courierId text of /api/v1/orders/accept/:id")
    @Description("Text 404 not courierId for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptNotCourierIDText() {
        //удалим курьера
        deleteCourier(courierId);
        putOrdersAccept(orderId, courierId).then().assertThat().body("message",equalTo("Курьера с таким id не существует"));
    }

    //если передать неверный номер заказа, запрос вернёт ошибку- код
    @Test
    @DisplayName("404-Check order Id error status code of /api/v1/orders/accept/:id")
    @Description("Status code 404 order Id error for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptIdErrorCode() {
        putOrdersAccept(0, courierId).then().statusCode(404);
    }

    //если передать неверный номер заказа, запрос вернёт ошибку- текст
    @Test
    @DisplayName("404-Check order Id error text of /api/v1/orders/accept/:id")
    @Description("Text 404 order Id error for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptIdErrorText() {
        putOrdersAccept(0, courierId).then().assertThat().body("message",equalTo("Заказа с таким id не существует"));

    }

    //Этот заказ уже в работе- код
    @Test
    @DisplayName("409-Check accept status > 0 status code of /api/v1/orders/accept/:id")
    @Description("Status code accept status > 0 for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptStatusCode() {
        putOrdersAccept(orderId, courierId);
        putOrdersAccept(orderId, courierId).then().statusCode(409);
    }
    //Этот заказ уже в работе- текст
    @Test
    @DisplayName("409-Check status > 0 text of /api/v1/orders/accept/:id")
    @Description("Text 409 status > 0 for /api/v1/orders/accept/:id")
    public void checkOrdersAcceptStatusText() {
        putOrdersAccept(orderId, courierId);
        putOrdersAccept(orderId, courierId).then().assertThat().body("message",equalTo("Этот заказ уже в работе"));

    }
    // Принять заказ
    @Step("Send Put request to /v1/orders/accept/...?courierId=..")
    public Response putOrdersAccept(Integer id, Integer courierId) {
        Response response = orders.acceptOrders(id, courierId);
        return response;
    }

    // Создаем курьера
    @Step("Send Post request to /api/v1/courier")
    public Response postRequestCourier(String bodyJason) {
        Response response = courier.addCourier(bodyJason);
        Integer id = courier.loginIdCourier(bodyJason);
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

    // Создаем заказа
    @Step("Send Post request to /api/v1/orders")
    public Response postRequestOrdersCreate(OrdersIN jason) {
        Response response = orders.addOrders(jason);
        trackId = response.then().extract().body().path("track");
        orderId = orders.trackOrders(trackId).then().extract().body().path("order.id");
        return response;
    }

    // Отмена заказа
    @Step("Send Put request to /api/v1/orders/cancel")
    public void putCancelOrders() {
        orders.cancelOrders(trackId);
    }

    @After
    public void cancelOrders() {
        //Удалим курьера
        deleteCourier(courierId);
    }




}
