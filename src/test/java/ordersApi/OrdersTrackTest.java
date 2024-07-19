package ordersApi;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.Test;
import java.util.List;
import orderIn.OrdersIN;

//ordersApi.Orders - Получить заказ по его номеру
public class OrdersTrackTest {
    OrdersStep ordersStep = new OrdersStep();
    OrdersIN json = new OrdersIN( "Naruto",
            "Uchiha",
            "Konoha, 142 apt.",
            "4",
            "+7 800 355 35 35",
            5,
            "2020-06-06",
            "Saske, come back to Konoha",
            List.of("BLACK"));


    //успешный запрос возвращает объект с заказом
    @Test
    @DisplayName("200-Check response order track")
    @Description("Status code 200 ")
    public void checkGetOrdersTrack() {
        ordersStep.checkOrdersTrackCode(json, 0,true,200, "");
    }

    //запрос без номера заказа возвращает ошибку
    @Test
    @DisplayName("400-Check order track absent ")
    @Description("Status code 400 absent track get")
    public void checkGetOrdersTrackAbsent() {
        ordersStep.checkOrdersTrackCode(json, null,false,400, "Недостаточно данных для поиска");
    }

    //запрос с несуществующим заказом возвращает ошибку- код
    @Test
    @DisplayName("404-Check order tract = 0 ")
    @Description("Status code 404 track = 0 get")
    public void checkGetOrdersTrackIdError() {
        ordersStep.checkOrdersTrackCode(json, 0,false,404, "Заказ не найден");
    }

}
