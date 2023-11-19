package onlineShop.service;

import onlineShop.dto.request.PasswordResetRequest;
import onlineShop.dto.response.MessageResponse;

public interface AuthenticationService {

    MessageResponse sendPasswordResetCode(String email);

    String getEmailByPasswordResetCode(String code);

    MessageResponse resetPassword(PasswordResetRequest request);
}
