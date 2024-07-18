import courier.CourierIn;
import io.restassured.response.Response;

public class Courier extends ApiScooter{
    public static final String URL_COURIER = "/api/v1/courier";
    public static final String URL_COURIER_LOGIN = "/api/v1/courier/login";

    //Создание курьера
    public Response addCourier(CourierIn bodyJason) {
        return doPostRequest(URL_COURIER, bodyJason);
    }

    //Удаление курьера
    public Response deleteCourier(Integer id) {
        String deletURL= URL_COURIER+"/";
        if (id != null){
            deletURL =URL_COURIER+"/"+id; }
        return  doDeleteRequest(deletURL);
    }

    //Логин курьера в системе
    public Response loginCourier(CourierIn bodyJason) {
        return doPostRequest(URL_COURIER_LOGIN,bodyJason);
    }

    public Integer loginIdCourier(CourierIn bodyJason) {
        Integer id = loginCourier(bodyJason).then().extract().body().path("id");
        return id;
    }
}
