package sopt.study.testcode.seongjae.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.seongjae.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.seongjae.api.service.order.response.OrderResponse;
import sopt.study.testcode.seongjae.domain.order.Order;
import sopt.study.testcode.seongjae.domain.order.OrderRepository;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;
import sopt.study.testcode.seongjae.domain.product.ProductType;
import sopt.study.testcode.seongjae.domain.stock.Stock;
import sopt.study.testcode.seongjae.domain.stock.StockRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final StockRepository stockRepository;

  public OrderResponse createOrder(
      final OrderCreateRequest request,
      final LocalDateTime registeredDateTime
  ) {
    List<String> productNumbers = request.getProductNumbers();
    // product
    final List<Product> products = findProductsBy(productNumbers);

    // 재고 차감이 필요한 상품 필터
    final List<String> stockProductNumbers = products.stream()
        .filter(product -> ProductType.containsStockType(product.getType()))
        .map(Product::getProductNumber)
        .toList();

    // 재고 엔티티 조회
    final List<Stock> stocks = stockRepository.findAllByProductNumberIn(
        stockProductNumbers);
    final Map<String, Stock> stockMap = stocks.stream()
        .collect(Collectors.toMap(Stock::getProductNumber, stock -> stock));

    // 상품 별 카운팅
    final Map<String, Long> productContingMap = stockProductNumbers.stream()
        .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

    // 재고 차감
    for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
      final Stock stock = stockMap.get(stockProductNumber);
      final int quantity = productContingMap.get(stockProductNumber).intValue();
      if (stock.isQuantityLessThan(quantity)) {
        throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
      }

      stock.deductQuantity(quantity);
    }

    // order
    Order order = Order.create(products, registeredDateTime);
    final Order savedOrder = orderRepository.save(order);

    return OrderResponse.of(savedOrder);
  }

  private List<Product> findProductsBy(final List<String> productNumbers) {
    final List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
    final Map<String, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getProductNumber, p -> p));

    return productNumbers.stream()
        .map(productMap::get)
        .toList();
  }
}
