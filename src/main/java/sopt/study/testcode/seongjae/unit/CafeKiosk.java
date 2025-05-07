package sopt.study.testcode.seongjae.unit;

import java.util.ArrayList;
import java.util.List;

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
}
