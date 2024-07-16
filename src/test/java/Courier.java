import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Courier {
    //Создание курьера
    public Response addCourier(String bodyJason) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyJason)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    //Удаление курьера
    public Response deleteCourier(Integer id) {

        String deletURL = String.format("/api/v1/courier/%s", id);

        Response response = given()
                .header("Content-type", "application/json")
                .delete(deletURL);
        return response;
    }

    // Удаление курьера запрос без id
    public Response deleteCourier400 () {

        String deletURL = String.format("/api/v1/courier/");

        Response response = given()
                .header("Content-type", "application/json")
                .delete(deletURL);
        return response;
    }
    //Логин курьера в системе
    public Response loginCourier(String bodyJason) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyJason)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    public Integer loginIdCourier(String bodyJason) {
        Integer id = loginCourier(bodyJason).then().extract().body().path("id");
        return id;
    }


}
