package ordersApi;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import orderIn.OrdersIN;

//ordersApi.Orders - Создание заказа
@RunWith(Parameterized.class)
public class OrdersCreateTest {
    Integer trackId;
    private List<String> color;
    private Integer code;

    OrdersStep ordersStep = new OrdersStep();

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

    @Test
    @DisplayName("Check create order status code")
    @Description("Code test create order")
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
        ordersStep.checkRequestOrdersCreate(json, code);
    }


}
