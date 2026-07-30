package day13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductSortDemo {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        products.add(new Product("键盘", 199.0, 500));
        products.add(new Product("鼠标", 99.0, 800));
        products.add(new Product("显示器", 1299.0, 200));

        products.sort(
                Comparator.comparingDouble(Product::getPrice)
        );

        for (Product product : products) {
            System.out.println(product);
        }
    }
}