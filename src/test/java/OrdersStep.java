import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import orderIn.OrdersIN;
import ordersOut.OrdersOut;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersStep {
    private Orders orders = new Orders();
    private ValidatableResponse response;

    // Принять заказ
    @Step("Send Put request orders accept")
    public void putOrdersAccept(Integer id, Integer courierId) {
        response = orders.acceptOrders(id, courierId).then();
    }

    // Создаем заказ Id вернем
    @Step("Send Post request orders create and track order Id")
    public Integer postRequestOrdersCreateId(OrdersIN jason) {
        response = orders.addOrders(jason).then();
        Integer trackId = response.extract().body().path("track");

        Integer id = orders.trackOrders(trackId).then().extract().body().path("order.id");
        return id;
    }

    // Получить созданный заказ или по trackId addOrder = false
    @Step("Send Get request orders track")
    public void getTrackCreateOrder(OrdersIN jason, Integer trackId, Boolean addOrder) {
        if (addOrder){
            trackId = orders.addOrders(jason).then().extract().body().path("track");
        }
        response = orders.trackOrders(trackId).then();
    }

    //Получение списка заказов
    @Step("Send get request list orders")
    public void getListOrders() {
        response = orders.getListOrders().then();
    }

    // Отмена заказа
    @Step("Send Put request orders cancel")
    public void putCancelOrders(Integer trackId) {
        orders.cancelOrders(trackId);
    }


    @Step("Check orders accept : code, message")
    public void checkOrdersAcceptCodeText(OrdersIN jason, Integer courierId, Integer statusCode, String statusText, Integer idOrder) {
        //Создадим, примим
        if (idOrder !=0) {
            idOrder =postRequestOrdersCreateId(jason);
        }
        //Примим
        putOrdersAccept(idOrder, courierId);
        //Проверили
        response.assertThat().statusCode(statusCode);
        response.body("message",equalTo(statusText));


    }


    @Step("Check orders accept : code, body")
    public void checkOrdersAcceptCodeBody(OrdersIN jason, Integer courierId, Integer statusCode, String bodyText) {
        //Создадим, примим
        Integer idOrder = postRequestOrdersCreateId(jason);
        putOrdersAccept(idOrder, courierId);
        //Проверим
        response.assertThat().statusCode(statusCode);
        String responseBody = response.extract().body().asString();
        MatcherAssert.assertThat(responseBody, equalTo(bodyText));
    }

    @Step("Check orders repeated accept : code, text")
    public void checkOrdersAcceptDublCodeText(OrdersIN jason, Integer courierId, Integer statusCode, String statusText) {
        //Создадим, примим
        Integer id = postRequestOrdersCreateId(jason);
        putOrdersAccept(id, courierId);
        //Примим повтороно
        putOrdersAccept(id, courierId);
        //Проверили
        response.assertThat().statusCode(statusCode);
        response.body("message",equalTo(statusText));
    }

    @Step("Check orders create: code, track")
    public void checkRequestOrdersCreate(OrdersIN jason, Integer statusCode) {
        //Создали
        postRequestOrdersCreateId(jason);
        //Проверили
        response.assertThat().statusCode(statusCode);
        response.assertThat().body("track", notNullValue());
        //Отменили
        Integer trackId = response.extract().body().path("track");
        putCancelOrders(trackId);
    }




    @Step("Check orders list: code, list")
    public void checkListOrders(Integer statusCode) {
        getListOrders();
        response.assertThat().statusCode(statusCode);
        OrdersOut orders = response.extract().body().as(OrdersOut.class);
        MatcherAssert.assertThat(orders, notNullValue());

    }





    @Step("Check orders track : code, body")
    public void checkOrdersTrackCode(OrdersIN jason, Integer trackId, Boolean addOrder, Integer statusCode, String statusText) {
        //Создадим и получить заказ по его номеру
        if (addOrder) {
            getTrackCreateOrder(jason, trackId, true);
        } else {
            getTrackCreateOrder(jason, trackId, false);
        }
        //проверим код
        response.assertThat().statusCode(statusCode);
        //тело
        if (statusText.equals("")) {
            OrdersOut orders = response.extract().body().as(OrdersOut.class);
            MatcherAssert.assertThat(orders, notNullValue());
        } else {
           response.body("message",equalTo(statusText));
        }


    }


}
