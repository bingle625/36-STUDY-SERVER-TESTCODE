package sopt.study.testcode.seongjae.unit;

import sopt.study.testcode.seongjae.unit.beverage.Americano;
import sopt.study.testcode.seongjae.unit.beverage.Latte;

public class CafeKioskRunner {

  public static void main(String[] args) {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());
    System.out.println(">>> 아메리칸 추가");
    cafeKiosk.add(new Latte());
    System.out.println(">>> 라테 추가");

    int totalPrice = cafeKiosk.calculateTotalPrice();
    System.out.println("총 주문 가격: " + totalPrice);
  }

}
