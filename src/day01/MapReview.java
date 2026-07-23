package day01;

import java.util.HashMap;
import java.util.Map;

public class MapReview {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(2, 0);
        map.put(7, 1);
        map.put(11, 2);

        System.out.println(map);
        System.out.println(map.get(7));
        System.out.println(map.containsKey(11));
        System.out.println(map.containsKey(100));
    }
}