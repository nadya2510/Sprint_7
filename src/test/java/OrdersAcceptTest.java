import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import courier.CourierIn;
import orderIn.OrdersIN;

//Orders - Принять заказ
public class OrdersAcceptTest extends ApiScooter{
    Integer courierId;
    OrdersStep ordersStep = new OrdersStep();
    CourierStep сourierStep = new CourierStep();
    OrdersIN json;

    @Before
    public void setUp() {
        //Создаем курьера
        CourierIn jsonCourier  = new CourierIn( "loginTest123",
                "1234",
                "firstName");
        CourierIn jsonLogin  = new CourierIn( "loginTest123",
                "1234");
        сourierStep.postRequestCourier(jsonCourier);
        courierId = сourierStep.loginCourier(jsonLogin);
        //Добавим заказ trackId
        json  = new OrdersIN( "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                List.of("BLACK"));
    }

    //успешный запрос возвращает
    @Test
    @DisplayName("200-Check accept order")
    @Description("Code test accept order")
    public void checkOrdersAccept() {
        ordersStep.checkOrdersAcceptCodeBody(json, courierId,200,"{\"ok\":true}");
    }


    //если не передать id курьера, запрос вернёт ошибку
    @Test
    @DisplayName("400-Check accept order courierId absent")
    @Description("Status code 400 accept order courierId absent")
    public void checkOrdersAcceptСourierIdAbsent() {
        ordersStep.checkOrdersAcceptCodeText(json, null,400,"Недостаточно данных для поиска", null);
    }

    //если передать неверный id курьера, запрос вернёт ошибку- код
    @Test
    @DisplayName("404-Check not courierId")
    @Description("Status code 404 test not courierId")
    public void checkOrdersAcceptNotCourierID() {
        //удалим курьера
        сourierStep.deleteCourier(courierId);
        ordersStep.checkOrdersAcceptCodeText(json, courierId,404,"Курьера с таким id не существует", null);

    }

    //если передать неверный номер заказа, запрос вернёт ошибку- код
    @Test
    @DisplayName("404-Check order Id error")
    @Description("Status code 404 order Id error")
    public void checkOrdersAcceptIdError() {
        ordersStep.checkOrdersAcceptCodeText(json, courierId,404,"Заказа с таким id не существует",0);
    }

    //Этот заказ уже в работе- код
    @Test
    @DisplayName("409-Check accept status > 0 ")
    @Description("Status code accept status > 0")
    public void checkOrdersAcceptStatus() {
        ordersStep.checkOrdersAcceptDublCodeText(json, courierId, 409, "Этот заказ уже в работе");
    }


    @After
    public void cancelOrders() {
        //Удалим курьера
        сourierStep.deleteCourier(courierId);
    }
}
