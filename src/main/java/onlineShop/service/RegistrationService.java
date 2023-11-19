package onlineShop.service;

import onlineShop.dto.response.MessageResponse;
import onlineShop.dto.request.UserRequest;

public interface RegistrationService {

    MessageResponse registration(String captchaResponse, UserRequest user);

    MessageResponse activateEmailCode(String code);
}
