package onlineShop.service;

import onlineShop.domain.Order;
import onlineShop.domain.User;
import onlineShop.dto.request.ChangePasswordRequest;
import onlineShop.dto.request.EditUserRequest;
import onlineShop.dto.request.SearchRequest;
import onlineShop.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getAuthenticatedUser();

    Page<Order> searchUserOrders(SearchRequest request, Pageable pageable);

    MessageResponse editUserInfo(EditUserRequest request);

    MessageResponse changePassword(ChangePasswordRequest request);
}
