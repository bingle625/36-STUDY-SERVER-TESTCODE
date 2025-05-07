package sopt.study.testcode.seongjae.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sopt.study.testcode.seongjae.unit.beverage.Beverage;
import sopt.study.testcode.seongjae.unit.order.Order;

public class CafeKiosk {

  private final List<Beverage> beverages = new ArrayList<>();

  public void add(final Beverage beverage) {
    beverages.add(beverage);
  }

  public void remove(Beverage beverage) {
    beverages.remove(beverage);
  }

  public void clear() {
    beverages.clear();
  }

  public int calculateTotalPrice() {
    int toalPrice = 0;
    for (Beverage beverage : beverages) {
      toalPrice += beverage.getPrice();
    }
    return toalPrice;
  }

  public Order createOrder() {
    return new Order(LocalDateTime.now(), beverages);
  }

  public List<Beverage> getBeverages() {
    return beverages;
  }
}
