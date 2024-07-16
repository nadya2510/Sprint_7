import java.util.List;

public class OrdersOut {
    private List<OrdersArray> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

    public List<OrdersArray> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersArray> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AvailableStations> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStations> availableStations) {
        this.availableStations = availableStations;
    }
}
