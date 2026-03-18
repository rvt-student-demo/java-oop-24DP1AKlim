package rvt.onlineShop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Warehouse {
  private Map<String, Integer> prices;
  private Map<String, Integer> stock;

  public Warehouse() {
    prices = new HashMap<>();
    stock = new HashMap<>();
  }

  public void addProduct(String product, int price, int stock) {
    prices.put(product, price);
    this.stock.put(product, stock);
  }

  public int price(String product) {
    if (!prices.containsKey(product)) return -99;
    return prices.get(product);
  }

  public int stock(String product) {
    if (!stock.containsKey(product)) return 0;
    return stock.get(product);
  }

  public boolean take(String product) {
    if (!stock.containsKey(product)) return false;

    int current = stock.get(product);

    if (current == 0) {
      return false;
    }

    stock.put(product, current - 1);
    return true;
  }

  public Set<String> products() {
    return prices.keySet();
  }
}
