package onlineShop.dto.response;

import onlineShop.domain.Order;
import onlineShop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private User user;
    private Page<Order> orders;
}
