import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.Matchers.*;

//Orders - Создание заказа
@RunWith(Parameterized.class)
public class OrdersCreateTest extends ApiScooter{
    Integer trackId;
    private List<String> color;
    private Integer code;
    Orders orders = new Orders();

    public OrdersCreateTest(List<String>  color, Integer code) {
        this.color = color;
        this.code = code;
    }

    @Parameterized.Parameters
    public static Object[][] getOrdersCreateTest() {
        //Тестовые данные
        return new Object[][]{
                {List.of("BLACK"), 201},
                {List.of("GREY"), 201},
                {List.of("BLACK", "GREY"), 201},
                {List.of(), 201}
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = urlApi;
    }

    @Test
    @DisplayName("Check create order status code of /api/v1/orders")
    @Description("Code test create order for /api/v1/orders")
    public void checkOrdersCreateTest() {
        OrdersIN json  = new OrdersIN( "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color);
        postRequestOrdersCreateTest(json).then().assertThat().body("track", notNullValue()).and().statusCode(code);
    }


    // Создаем заказа
    @Step("Send Post request to /api/v1/orders")
    public Response postRequestOrdersCreateTest(OrdersIN jason) {
        Response response = orders.addOrders(jason);
        trackId = response.then().extract().body().path("track");
        return response;
    }


    // Отмена заказа
    @Step("Send Put request to /api/v1/orders/cancel")
    public void putCancelOrders() {
        orders.cancelOrders(trackId);
    }

    @After
    public void cancelOrders() {
        //Отмена заказа
        putCancelOrders();
    }

}
