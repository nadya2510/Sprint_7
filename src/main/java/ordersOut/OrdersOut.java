package ordersOut;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersOut {
    private List<OrdersArray> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

}
