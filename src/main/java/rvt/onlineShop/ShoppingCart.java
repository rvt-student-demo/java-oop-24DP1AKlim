package rvt.onlineShop;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
  private Map<String, Item> products;

  public ShoppingCart() {
    products = new HashMap<>();
  }

  public void add(String product, int price) {
    if (products.containsKey(product)) {
      products.get(product).increaseQuantity();
    } else {
      products.put(product, new Item(product, 1, price));
    }
  }

  public int price() {
    int total = 0;
    for (Item product : products.values()) {
      total += product.price();
    }
    return total;
  }

  public void print() {
    for (Item product : products.values()) {
      System.out.println(product);
    }
  }
}
