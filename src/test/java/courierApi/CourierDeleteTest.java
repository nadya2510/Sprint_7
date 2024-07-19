package courierApi;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import courier.CourierIn;

//courierApi.Courier - Удаление курьера
public class CourierDeleteTest{
    CourierStep сourierStep = new CourierStep();

    CourierIn json  = new CourierIn( "loginTest123",
            "1234",
            "firstName");
    CourierIn jsonLogin  = new CourierIn( "loginTest123",
            "1234");

    @Before
    public void setUp() {
        //Создадим курьера
        сourierStep.postRequestCourier(json);
    }

    //успешный запрос;
    @Test
    @DisplayName("200-Check courier delete")
    @Description("Status code 200 courier")
    public void checkCourierDelete() {
        сourierStep.checkCourierDeleteCodeBody(jsonLogin,200,"{\"ok\":true}");
    }

    //если отправить запрос с несуществующим id, вернётся ошибка
    @Test
    @DisplayName("404-Check id not found courier delete")
    @Description("Status code 404 id not found courier delete")
    public void checkCourierDeleteIdNotFound() {
        //удалим курьера
        сourierStep.deleteCourierLogin(jsonLogin);
        //повторно удалим
        сourierStep.checkCourierDeleteCodeText(jsonLogin,404,"Курьера с таким id нет");
    }

    //если отправить запрос без id, вернётся ошибка
    @Test
    @DisplayName("400-Check id absent delete status code")
    @Description("Status cod 400 id absent delete")
    public void checkCourierDeleteIdAbsent() {
        сourierStep.checkCourierDeleteCodeText(null, 400,"Недостаточно данных для удаления курьера");
    }

}
