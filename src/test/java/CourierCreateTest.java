import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import courier.CourierIn;

//Courier - Создание курьера
public class CourierCreateTest  {
    CourierStep сourierStep = new CourierStep();
    CourierIn json  = new CourierIn( "loginTest123",
            "1234",
            "firstName");
    CourierIn jsonLogin  = new CourierIn( "loginTest123",
            "1234");

    //курьера можно создать, чтобы создать курьера, нужно передать в ручку все обязательные поля
    @Test
    @DisplayName("201-Check create courier")
    @Description("Status code 201 test")
    public void checkCreatedCourier() {
        сourierStep.postRequestCourier(json);
        сourierStep.checkCourierCodeBody(201,"{\"ok\":true}");
    }

    //если создать пользователя с логином (пароли разные), который уже есть, возвращается ошибка
    @Test
    @DisplayName("409-Check create duplicate login")
    @Description("Status code 409 duplicate login")
    public void checkCreatedDuplicateLogin() {
        сourierStep.postRequestCourier(json);
        CourierIn jsonTest  = new CourierIn( "loginTest123",
                "12345",
                "firstName");
        сourierStep.postRequestCourier(jsonTest);
        сourierStep.checkCourierCodeText(409,"Этот логин уже используется");
    }

    //нельзя создать двух одинаковых курьеров
    @Test
    @DisplayName("409-Check create duplicate courier")
    @Description("Status code 409 duplicate courier")
    public void checkCreatedDuplicateCourier() {
        сourierStep.postRequestCourier(json);
        сourierStep.postRequestCourier(json);
        сourierStep.checkCourierCodeText(409,"Этот логин уже используется");
    }

    //если одного из полей (FirstName) нет, запрос возвращает ошибку
    @Test
    @DisplayName("400-Check create courier firstName absent")
    @Description("Status code 400 firstName absent")
    public void checkCreatedCourierFirstNameAbsent() {
        сourierStep.postRequestCourier(jsonLogin);
        сourierStep.checkCourierCodeText(400,"Недостаточно данных для создания учетной записи");
    }

    //если одного из полей (Password) нет, запрос возвращает ошибку
    @Test
    @DisplayName("400-Check create courier password absent")
    @Description("Status cod 400 password absent")
    public void checkCreatedCourierPasswordAbsent() {
       CourierIn jsonTest  = new CourierIn( "loginTest123",
                null,
                "firstName");
        сourierStep.postRequestCourier(jsonTest);
        сourierStep.checkCourierCodeText(400,"Недостаточно данных для создания учетной записи");

    }

    //если одного из полей (login) нет, запрос возвращает ошибку
    @Test
    @DisplayName("400-Check create courier login absent")
    @Description("Status cod 400 login absent")
    public void checkCreatedCourierLoginAbsent() {
        CourierIn jsonTest = new CourierIn( null,
                "1234",
                "firstName");
        сourierStep.postRequestCourier(jsonTest);
        сourierStep.checkCourierCodeText(400,"Недостаточно данных для создания учетной записи");
    }

    @After
    public void deleteCourier() {
        сourierStep.deleteCourierLogin(jsonLogin);

    }

}


