import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import courier.CourierIn;

public class CourierLoginTest extends ApiScooter{
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

    //курьер может авторизоваться;
    //для авторизации нужно передать все обязательные поля
    @Test
    @DisplayName("200-Check courier login")
    @Description("Status code 200 courier login")
    public void checkCourierLogin() {
        сourierStep.checkLoginCourierBody(jsonLogin, 200, null, true);
    }

    //для авторизации нужно передать все обязательные поля (login отсуствует);
    @Test
    @DisplayName("400-Check login absent")
    @Description("Status code 400 login absent")
    public void checkCourierLoginLoginAbsent() {
        CourierIn jsonTest  = new CourierIn( null,
                "1234",
                null);
        сourierStep.checkLoginCourierBody(jsonTest, 400, "Недостаточно данных для входа", false);
    }

    //для авторизации нужно передать все обязательные поля (password отсуствует);
    @Test
    @DisplayName("400-Check password absent")
    @Description("Status code 400 password absent")
    public void checkCourierLoginPasswordAbsent() {
        CourierIn jsonTest  = new CourierIn( "loginTest123",
                null,
                null);
        сourierStep.checkLoginCourierBody(jsonTest, 400, "Недостаточно данных для входа", false);
    }

    //система вернёт ошибку, если неправильно указать логин ;
    @Test
    @DisplayName("404-Check login error")
    @Description("Status code 404 login error")
    public void checkCourierLoginLoginError() {
        CourierIn jsonTest  = new CourierIn( "Test1234",
                "1234",
                "firstName");
        сourierStep.checkLoginCourierBody(jsonTest, 404, "Учетная запись не найдена", false);
    }

    //система вернёт ошибку, если неправильно указать пароль;
    @Test
    @DisplayName("404-Check password error")
    @Description("Status code 404 password error")
    public void checkCourierLoginPasswordErrore() {
       CourierIn jsonTest  = new CourierIn( "Test1234",
                "4321",
                "firstName");
        сourierStep.checkLoginCourierBody(jsonTest, 404, "Учетная запись не найдена", false);
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("404-Check not courier")
    @Description("Status code 404 not courier")
    public void checkCourierLoginNotCourier() {
        сourierStep.deleteCourierLogin(jsonLogin);
        сourierStep.checkLoginCourierBody(jsonLogin, 404, "Учетная запись не найдена", false);
    }

    @After
    public void deleteCourier(){
        сourierStep.deleteCourierLogin(jsonLogin);
    }
}
