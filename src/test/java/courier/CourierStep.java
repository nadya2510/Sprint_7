package courierApi;

import courier.CourierIn;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierStep {
    private Courier courier = new Courier();
    private ValidatableResponse response;

    // Создаем курьера
    @Step("Send Post add courier request")
    public void postRequestCourier(CourierIn bodyJason) {
        response = courier.addCourier(bodyJason).then();
    }

    // Получаем id курьера
    @Step("Send Post courier login request")
    public Integer loginCourier(CourierIn bodyJason) {
        response = courier.loginCourier(bodyJason).then();
        Integer id = response.extract().body().path("id");;
        return id;
    }

    // Удаляем курьера
    @Step("Send Delete courier request")
    public void deleteCourier(Integer id) {
        response = courier.deleteCourier(id).then();
    }

    // Удаляем курьера по логину
    @Step("Send Delete courier request")
    public void deleteCourierLogin(CourierIn bodyJason) {
        Integer id = courier.loginIdCourier(bodyJason);
        deleteCourier(id);
    }
    // Проверяем создание курьера по коду и тексту сообщения
    @Step("Check courier create : code, message")
    public void checkCourierCodeText(Integer statusCode, String statusText) {
        response.assertThat().statusCode(statusCode);
        response.body("message",equalTo(statusText));
    }
    // Проверяем создание курьера по коду и тело
    @Step("Check courier create : code, body")
    public void checkCourierCodeBody(Integer statusCode, String bodyText) {
        response.assertThat().statusCode(statusCode);
        String responseBody = response.extract().body().asString();
        MatcherAssert.assertThat(responseBody, equalTo(bodyText));
    }

    // Проверяем удаление курьера по коду и тексту сообщения
    @Step("Check courier delete : code, message")
    public void checkCourierDeleteCodeText(CourierIn bodyJason, Integer statusCode, String statusText) {
        Integer id = null;
        if (bodyJason != null){
           id = courier.loginIdCourier(bodyJason);
        }
        deleteCourier(id);
        response.assertThat().statusCode(statusCode);
        response.body("message",equalTo(statusText));
    }

    // Проверяем удаление курьера по коду и тело
    @Step("Check courier delete : code, body")
    public void checkCourierDeleteCodeBody(CourierIn bodyJason,Integer statusCode, String bodyText) {
        Integer id = courier.loginIdCourier(bodyJason);
        deleteCourier(id);
        response.assertThat().statusCode(statusCode);
        String responseBody = response.extract().body().asString();
        MatcherAssert.assertThat(responseBody, equalTo(bodyText));
    }

    //Логин курьера в системе
    @Step("Check courier login")
    public void checkLoginCourierBody(CourierIn bodyJason, Integer statusCode, String statusText, Boolean idResponse) {
        response = courier.loginCourier(bodyJason).then();
        response.assertThat().statusCode(statusCode);
        if (statusText != null){
           response.body("message",equalTo(statusText));
        };
        if (idResponse){
           response.assertThat().body("id", notNullValue());
        }


    }
}
