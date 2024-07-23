package ordersOut;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersArray {
    private Integer id;
    private Integer courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private Integer track;
    private List<String> color;
    private String comment;
    private String createdAt;
    private String updatedAt;
    private Integer status;


}
