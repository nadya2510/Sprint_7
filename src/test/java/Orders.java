import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Orders {
    //Создание заказа
    public Response addOrders(OrdersIN jason) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(jason)
                .when()
                .post("/api/v1/orders");
        return response;
    }
    //Отменить заказ
    public Response cancelOrders(Integer trackId) {
        String jason = String.format("{\"track\": %s}", trackId);
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(jason)
                .when()
                .put("/api/v1/orders/cancel");
        return response;
    }
    //Получение списка заказов.
    public Response getSpisokOrders() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/v1/orders");
        return response;
    }
    //Принять заказ
    public Response acceptOrders(Integer id, Integer courierId){
        String putS = String.format("/api/v1/orders/accept/%d", id);
        Response response;
        if  (courierId != null){
            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .when()
                    .queryParam("courierId", courierId)
                    .put(putS);
        } else {
            putS = String.format("/api/v1/orders/accept/%d", id);
            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .when()
                    .put(putS);
        }

        return response;
    }

    //Получить заказ по его номеру
    public Response trackOrders(Integer track){
        Response response;
        if (track !=null){
         response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .queryParam("t", track)
                .get("/api/v1/orders/track");
        } else {
             response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .when()
                    .get("/api/v1/orders/track");
        }
        return response;
    }
}
