import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.notNullValue;

//Orders - Получение списка заказов.
public class OrdersTest extends ApiScooter{
    Orders orders = new Orders();

    @Before
    public void setUp() {
        RestAssured.baseURI = urlApi;
    }

    @Test
    @DisplayName("Check response get of /api/v1/orders")
    @Description("Response body get for /api/v1/orders")
    public void checkGetSpisokOrders() {
        OrdersOut orders = getSpisokOrders().body().as(OrdersOut.class);
        MatcherAssert.assertThat(orders, notNullValue());
    }

    @Test
    @DisplayName("Check status code get of /api/v1/orders")
    @Description("Status code 200 get for /api/v1/orders")
    public void checkGetSpisokOrdersCode() {
        getSpisokOrders().then().statusCode(200);

    }

    // Получение списка заказов.
    @Step("Send Get request to /api/v1/orders")
    public Response getSpisokOrders() {
        Response response = orders.getSpisokOrders();
        return response;
    }

}
