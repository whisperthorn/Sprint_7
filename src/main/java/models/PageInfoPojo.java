package models;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class PageInfoPojo {
    private int page;
    private int total;
    private int limit;
}
