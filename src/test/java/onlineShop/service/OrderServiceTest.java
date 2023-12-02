package onlineShop.service;

import onlineShop.domain.Order;
import onlineShop.domain.Perfume;
import onlineShop.domain.User;
import onlineShop.dto.request.OrderRequest;
import onlineShop.repository.OrderRepository;
import onlineShop.service.impl.MailService;
import onlineShop.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private UserService userService;
    @Mock
    private MailService mailService;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void OrderService_GetOrder_ReturnFoundOrder() {
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Perfume> perfumes = createPerfumeList();
        User user = createUser(perfumes);
        Order order = createOrder(localDateTime, user, perfumes);
        Optional<Order> optionalOrder = Optional.of(order);

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(orderRepository.getByIdAndUserId(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(optionalOrder);

        // Act
        Order foundOrder = orderService.getOrder(order.getId());

        // Assert
        assertNotNull(foundOrder);
        assertSame(order, foundOrder);
    }

    private List<Perfume> createPerfumeList() {
        List<Perfume> perfumes = new ArrayList<>();
        Perfume perfume1 = new Perfume();
        perfume1.setId(1L);
        perfume1.setCountry("Germany");
        perfume1.setDescription(null);
        perfume1.setFilename("4735a4c8-e1fc-43ce-8ff9-0bd28d00ce20.Hugo Boss Boss Bottled Night.jpg");
        perfume1.setFragranceBaseNotes("Salmwood, Musky notes, Sandalwood");
        perfume1.setFragranceMiddleNotes("Cardamom, Jasmine, African violet");
        perfume1.setFragranceTopNotes("Birch leaf, Lavender");
        perfume1.setPerfumeGender("male");
        perfume1.setPerfumeTitle("Boss Bottled Night");
        perfume1.setPerfumer("Hugo Boss");
        perfume1.setPrice(35);
        perfume1.setType("Eau de Toilette");
        perfume1.setVolume("100");
        perfume1.setYear(2010);
        perfumes.add(perfume1);
        return perfumes;
    }

    private User createUser(List<Perfume> perfumes) {
        User user = new User();
        user.setId(3L);
        user.setEmail("test1234@test.com");
        user.setFirstName("first_name");
        user.setLastName("last_name");
        user.setCity("city");
        user.setAddress("address");
        user.setPhoneNumber("1234567890");
        user.setActivationCode("1234567890");
        user.setActivationCode(null);
        user.setActive(true);
        user.setPassword("$2a$08$eApn9x3qPiwp6cBVRYaDXed3J/usFEkcZbuc3FDa74bKOpUzHR.S.");
        user.setPasswordResetCode(null);
        user.setPerfumeList(perfumes);
        return user;
    }

    private Order createOrder(LocalDateTime localDateTime, User user, List<Perfume> perfumes) {
        Order order = new Order();
        order.setId(2L);
        order.setAddress("address");
        order.setCity("city");
        order.setDate(localDateTime);
        order.setEmail("test1234@test.com");
        order.setFirstName("first_name");
        order.setLastName("last_name");
        order.setPhoneNumber("1234567890");
        order.setPostIndex(1234567890);
        order.setTotalPrice(56.0);
        order.setUser(user);
        order.setPerfumes(perfumes);
        return order;
    }

    @Test
    public void OrderService_GetOrdering_ReturnFoundPerfumesList() {
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Perfume> perfumes = createPerfumeList();
        User user = createUser(perfumes);

        when(userService.getAuthenticatedUser()).thenReturn(user);

        // Act
        List<Perfume> foundPerfumes = orderService.getOrdering();

        // Assert
        assertNotNull(foundPerfumes);
        assertSame(perfumes, foundPerfumes);
    }

    @Test
    public void OrderService_GetUserOrdersList_ReturnFoundOrders() {
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Perfume> perfumes = createPerfumeList();
        User user = createUser(perfumes);
        Order order = createOrder(localDateTime, user, perfumes);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<Order> ordersPage = new PageImpl<>(orderList);

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(orderRepository.findOrderByUserId(Mockito.any(Long.class), Mockito.any(Pageable.class))).thenReturn(ordersPage);

        // Act
        Page<Order> foundOrders = orderService.getUserOrdersList(pageRequest);

        // Assert
        assertNotNull(foundOrders);
        assertSame(ordersPage, foundOrders);
    }

    @Test
    public void OrderService_PostOrder_ReturnOrderId() {
        // Arrange
        ModelMapper arrangeModelMapper = new ModelMapper();

        LocalDateTime localDateTime = LocalDateTime.now();
        List<Perfume> perfumes = createPerfumeList();
        User user = createUser(perfumes);
        Order order = createOrder(localDateTime, user, perfumes);
        OrderRequest orderRequest = arrangeModelMapper.map(order, OrderRequest.class);
        orderRequest.setId(null);
        Order mapedOrder = arrangeModelMapper.map(orderRequest, Order.class);

        when(modelMapper.map(Mockito.any(OrderRequest.class), Mockito.eq(Order.class))).thenReturn(mapedOrder);
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        Mockito.doNothing().when(mailService).sendMessageHtml(Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.anyMap());

        // Act
        Long orderId = orderService.postOrder(user, orderRequest);

        // Assert
        assertNotNull(orderId);
        assertEquals(order.getId(), orderId);
    }

}