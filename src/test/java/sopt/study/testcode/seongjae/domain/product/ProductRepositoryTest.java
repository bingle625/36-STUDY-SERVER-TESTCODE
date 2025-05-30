package sopt.study.testcode.seongjae.domain.product;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.HOLD;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.SELLING;
import static sopt.study.testcode.seongjae.domain.product.ProductSellingStatus.STOP_SELLING;
import static sopt.study.testcode.seongjae.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
  @Test
  void findAllBySellingStatusIn() {
    // given
    final Product product1 = Product.builder()
        .productNumber("001")
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("아메리카노")
        .price(4000)
        .build();

    final Product product2 = Product.builder()
        .productNumber("002")
        .type(HANDMADE)
        .sellingStatus(HOLD)
        .name("까페라테")
        .price(4500)
        .build();

    final Product product3 = Product.builder()
        .productNumber("003")
        .type(HANDMADE)
        .sellingStatus(STOP_SELLING)
        .name("팥빙수")
        .price(7000)
        .build();
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    final List<Product> products = productRepository.findAllBySellingStatusIn(
        List.of(SELLING, HOLD));

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "아메리카노", SELLING),
            tuple("002", "까페라테", HOLD)
        );

  }

  @DisplayName("상품 번호 리스트로 상품들을 조회한다.")
  @Test
  void findAllByProductNumberIn() {

    // given
    final Product product1 = Product.builder()
        .productNumber("001")
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("아메리카노")
        .price(4000)
        .build();

    final Product product2 = Product.builder()
        .productNumber("002")
        .type(HANDMADE)
        .sellingStatus(HOLD)
        .name("까페라테")
        .price(4500)
        .build();

    final Product product3 = Product.builder()
        .productNumber("003")
        .type(HANDMADE)
        .sellingStatus(STOP_SELLING)
        .name("팥빙수")
        .price(7000)
        .build();
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    final List<Product> products = productRepository.findAllByProductNumberIn(
        List.of("001", "002")
    );

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "아메리카노", SELLING),
            tuple("002", "까페라테", HOLD)
        );

  }
}