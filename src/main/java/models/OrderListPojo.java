package models;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderListPojo {
    private List<OrderInfoPojo> orders;
    private PageInfoPojo pageInfo;
    private List<AvailableStationPojo> availableStations;
}
