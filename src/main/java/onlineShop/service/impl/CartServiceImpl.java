package onlineShop.service.impl;

import onlineShop.domain.Perfume;
import onlineShop.domain.User;
import onlineShop.repository.PerfumeRepository;
import onlineShop.service.CartService;
import onlineShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final PerfumeRepository perfumeRepository;

    @Override
    public List<Perfume> getPerfumesInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getPerfumeList();
    }

    @Override
    @Transactional
    public void addPerfumeToCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().add(perfume);
    }

    @Override
    @Transactional
    public void removePerfumeFromCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().remove(perfume);
    }
}
