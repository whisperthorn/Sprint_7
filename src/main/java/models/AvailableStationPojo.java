package models;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AvailableStationPojo {
    private String name;
    private String number;
    private String color;
}
