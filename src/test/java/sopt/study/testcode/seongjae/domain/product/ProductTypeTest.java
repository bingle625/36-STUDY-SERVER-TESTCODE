package sopt.study.testcode.seongjae.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

  @DisplayName("상품 타입이 재고관련 타입인지를 체크한다.")
  @Test
  void containsStockType() {
    // given
    final ProductType givenType = ProductType.HANDMADE;

    // when
    final boolean result = ProductType.containsStockType(givenType);

    // then
    assertThat(result).isFalse();
  }
}