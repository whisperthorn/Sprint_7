package models;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class OrderInfoPojo {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private int track;
    private int status;
    private String[] color;
    private String comment;
    private boolean cancelled;
    private boolean finished;
    private boolean inDelivery;
    private String courierFirstName;
    private String createdAt;
    private String updatedAt;
}
