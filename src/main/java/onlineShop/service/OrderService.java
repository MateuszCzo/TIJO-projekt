package onlineShop.service;

import onlineShop.domain.Order;
import onlineShop.domain.Perfume;
import onlineShop.domain.User;
import onlineShop.dto.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order getOrder(Long orderId);

    List<Perfume> getOrdering();

    Page<Order> getUserOrdersList(Pageable pageable);

    Long postOrder(User user, OrderRequest orderRequest);
}
