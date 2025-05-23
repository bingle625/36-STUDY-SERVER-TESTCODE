package sopt.study.testcode.seongjae.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.study.testcode.seongjae.api.controller.order.request.OrderCreateRequest;
import sopt.study.testcode.seongjae.api.service.order.response.OrderResponse;
import sopt.study.testcode.seongjae.domain.order.Order;
import sopt.study.testcode.seongjae.domain.order.OrderRepository;
import sopt.study.testcode.seongjae.domain.product.Product;
import sopt.study.testcode.seongjae.domain.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public OrderResponse createOrder(
      final OrderCreateRequest request,
      final LocalDateTime registeredDateTime
  ) {
    List<String> productNumbers = request.getProductNumbers();
    // product
    final List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

    // order
    Order order = Order.create(products, registeredDateTime);
    final Order savedOrder = orderRepository.save(order);

    return OrderResponse.of(savedOrder);
  }
}
