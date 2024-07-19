package ordersApi;

import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

//ordersApi.Orders - Получение списка заказов.
public class OrdersTest {
    OrdersStep ordersStep = new OrdersStep();

    @Test
    @DisplayName("Check response orders list get")
    @Description("Response body and code ")
    public void checkGetSpisokOrders() {
        ordersStep.checkListOrders(200);

    }
}
