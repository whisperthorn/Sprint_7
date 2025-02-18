package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataLoader {
    private static List<OrderCreatePojo> loadTestData(String filePath) throws Exception {
        Gson gson = new Gson();
        Type orderListType = new TypeToken<List<OrderCreatePojo>>() {}.getType();
        return gson.fromJson(new FileReader(filePath), orderListType);
    }

    public static Collection<Object[]> getTestData(String filePath) throws Exception {
        List<OrderCreatePojo> orders = loadTestData(filePath);
        return orders.stream()
                .map(order -> new Object[]{order})
                .collect(Collectors.toList());
    }

    public static OrderCreatePojo getFirstTestData(String filePath) throws Exception {
        List<OrderCreatePojo> orders = loadTestData(filePath);
        return orders.get(0);
    }


}
