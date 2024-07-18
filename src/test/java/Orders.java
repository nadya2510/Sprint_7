import io.restassured.response.Response;
import orderIn.OrdersIN;
import java.util.HashMap;
import java.util.Map;


public class Orders extends ApiScooter{
    public static final String URL_ORDERS = "/api/v1/orders";
    public static final String URL_ORDERS_CANCEL = "/api/v1/orders/cancel";
    public static final String URL_ORDERS_ACCEPT = "/api/v1/orders/accept";
    public static final String URL_ORDERS_TRACK = "/api/v1/orders/track";
    //Создание заказа
    public Response addOrders(OrdersIN jason) {
        return doPostRequest(URL_ORDERS,jason);
    }
    //Отменить заказ
    public Response cancelOrders(Integer trackId) {
        String jason = String.format("{\"track\": %s}", trackId);
        return doPutRequest(URL_ORDERS_CANCEL,jason);
    }
    //Получение списка заказов.
    public Response getListOrders() {
        return doGetRequest(URL_ORDERS);
    }
    //Принять заказ
    public Response acceptOrders(Integer id, Integer courierId){
        String putS = URL_ORDERS_ACCEPT+"/"+id;
        Map<String, Integer> params = new HashMap<>();
        params.put("courierId", courierId);
        Response response = doPutRequest(putS, params);
        return response;
    }

    //Получить заказ по его номеру
    public Response trackOrders(Integer track){
        Response response;
        if (track !=null){
         Map<String, Integer> params = new HashMap<>();
         params.put("t", track);
         response = doGetRequest(URL_ORDERS_TRACK,params);

        } else {
             response = doGetRequest(URL_ORDERS_TRACK);        }
        return response;
    }
}
