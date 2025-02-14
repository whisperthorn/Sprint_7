package models;

import java.util.List;

public class OrderListPojo {
    private List<OrderInfoPojo> orders;
    private PageInfoPojo pageInfo;
    private List<AvailableStationPojo> availableStations;

    public List<OrderInfoPojo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfoPojo> orders) {
        this.orders = orders;
    }

    public PageInfoPojo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoPojo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AvailableStationPojo> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStationPojo> availableStations) {
        this.availableStations = availableStations;
    }
}
