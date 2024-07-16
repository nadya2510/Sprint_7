import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.hamcrest.MatcherAssert;
import java.util.List;
import static org.hamcrest.Matchers.*;

//Orders - Получить заказ по его номеру
public class OrdersTrackTest extends ApiScooter {
    Orders orders = new Orders();
    Integer trackId;
    Integer orderId;


    @Before
    public void setUp() {
        RestAssured.baseURI = urlApi;

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

    //успешный запрос возвращает объект с заказом- код
    @Test
    @DisplayName("200-Check response get status code of /api/v1/orders/track")
    @Description("Status code 200 get for /api/v1/orders/track")
    public void checkGetOrdersTrackCode() {
        getRequestOrdersTrack(trackId).then().statusCode(200);
    }

    //успешный запрос возвращает объект с заказом- тело
    @Test
    @DisplayName("200-Check response get body of /api/v1/orders/track")
    @Description("Response body get for /api/v1/orders/track")
    public void checkGetOrdersTrackBody() {
        Response response = getRequestOrdersTrack(trackId);
        OrdersOut orders = response.body().as(OrdersOut.class);
        MatcherAssert.assertThat(orders, notNullValue());
        printResponseBodyToConsole(response);
    }

    //запрос без номера заказа возвращает ошибку- код
    @Test
    @DisplayName("400-Check absent track get status code of /api/v1/orders/track")
    @Description("Status code absent track get for /api/v1/orders/track")
    public void checkGetOrdersTrackAbsentCode() {
        getRequestOrdersTrack(null).then().statusCode(400);
    }

    //запрос без номера заказа возвращает ошибку- текст
    @Test
    @DisplayName("400-Check absent track get text of /api/v1/orders/track")
    @Description("Text 400 absent track get for /api/v1/orders/track")
    public void checkGetOrdersTrackAbsentText() {
       getRequestOrdersTrack(null).then().assertThat().body("message",equalTo("Недостаточно данных для поиска"));
    }

    //запрос с несуществующим заказом возвращает ошибку- код
    @Test
    @DisplayName("404-Check track = 0 get status code of /api/v1/orders/track")
    @Description("Status code track = 0 get for /api/v1/orders/track")
    public void checkGetOrdersTrack0Code() {
        getRequestOrdersTrack(0).then().statusCode(404);
    }

    //запрос с несуществующим заказом возвращает ошибку- текст
    @Test
    @DisplayName("404-Check track = 0 get text of /api/v1/orders/track")
    @Description("Text track = 0 get for /api/v1/orders/track")
    public void checkGetOrdersTrack0Text() {
        getRequestOrdersTrack(0).then().assertThat().body("message",equalTo("Заказ не найден"));
    }

    // Создаем заказа
    @Step("Send Post request to /api/v1/orders")
    public Response postRequestOrdersCreate(OrdersIN jason) {
        Response response = orders.addOrders(jason);
        trackId = response.then().extract().body().path("track");
        orderId = orders.trackOrders(trackId).then().extract().body().path("order.id");
        return response;
    }

    // Получить заказ по его номеру
    @Step("Send Get request to /api/v1/orders/track")
    public Response getRequestOrdersTrack(Integer RequestTrackId) {
        Response response = orders.trackOrders(RequestTrackId);
        return response;
    }

    // Отмена заказа
    @Step("Send Put request to /api/v1/orders/cancel")
    public void putCancelOrders() {
        orders.cancelOrders(trackId);
    }

    //Отправим на экран
    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response){
        System.out.println(response.body().asString());
    }

    @After
    public void cancelOrders() {
        //Отмена заказа
        putCancelOrders();
    }

}
