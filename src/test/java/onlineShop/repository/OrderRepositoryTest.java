package onlineShop.repository;

import onlineShop.domain.Order;
import onlineShop.domain.Perfume;
import onlineShop.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-perfumes-before.sql", "/sql/create-user-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-orders-after.sql", "/sql/create-user-after.sql", "/sql/create-perfumes-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PerfumeRepository perfumeRepository;

    @Test
    public void OrderRepository_GetById_ReturnFoundOrder() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Long orderId = 1L;

        // Act
        Optional<Order> foundOrderOptional = orderRepository.getById(orderId);

        // Assert
        assertTrue(foundOrderOptional.isPresent());

        Order foundOrder = foundOrderOptional.get();

        assertEquals("address", foundOrder.getAddress());
        assertEquals("city", foundOrder.getCity());
        assertEquals("2023-11-29T00:00", foundOrder.getDate().toString());
        assertEquals("test123@test.com", foundOrder.getEmail());
        assertEquals("first_name", foundOrder.getFirstName());
        assertEquals("last_name", foundOrder.getLastName());
        assertEquals("1234567890", foundOrder.getPhoneNumber());
        assertEquals(56, foundOrder.getTotalPrice());
        assertEquals(2, foundOrder.getUser().getId());

        List<Perfume> perfumeList = foundOrder.getPerfumes();

        assertEquals(2, perfumeList.size());
        assertEquals(1, perfumeList.get(0).getId());
        assertEquals(2, perfumeList.get(1).getId());
    }

    @Test
    public void OrderRepository_FindAll_ReturnFoundOrders() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Order> foundOrders = orderRepository.findAll(pageRequest);

        // Assert
        assertNotNull(foundOrders);
        assertEquals(1, foundOrders.getTotalPages());
        assertEquals(1, foundOrders.getTotalElements());

        List<Order> list = foundOrders.getContent();

        assertEquals(1, list.size());
        for (Order order : list) {
            assertNotNull(order);
        }
    }

    @Test
    public void OrderRepository_SearchOrders_ReturnFoundOrders() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String searchType = "email";
        String text = "test123@test.com";
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Order> foundOrders = orderRepository.searchOrders(searchType, text, pageRequest);

        // Assert
        List<Order> list = foundOrders.getContent();

        assertEquals(1, list.size());//0
        for (Order order : list) {
            assertTrue(order.getEmail().contains(text));
        }
    }

    @Test
    public void OrderRepository_FindOrderByUserId_ReturnFoundOrder() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Long userId = 2L;
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Order> foundOrders = orderRepository.findOrderByUserId(userId, pageRequest);

        // Assert
        List<Order> list = foundOrders.getContent();

        assertEquals(1, list.size());
        assertEquals(2, list.get(0).getUser().getId());
    }

    @Test
    public void OrderRepository_GetByIdAndUserId_ReturnFoundOrder() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Long orderId = 1L;
        Long userId = 2L;
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Optional<Order> foundOrdersOptional = orderRepository.getByIdAndUserId(orderId, userId);

        // Assert
        assertTrue(foundOrdersOptional.isPresent());

        Order foundOrder = foundOrdersOptional.get();

        assertNotNull(foundOrder);
        assertNotNull(foundOrder.getId());
        assertEquals("address", foundOrder.getAddress());
        assertEquals("city", foundOrder.getCity());
        assertEquals("2023-11-29T00:00", foundOrder.getDate().toString());
        assertEquals("test123@test.com", foundOrder.getEmail());
        assertEquals("first_name", foundOrder.getFirstName());
        assertEquals("last_name", foundOrder.getLastName());
        assertEquals("1234567890", foundOrder.getPhoneNumber());
        assertEquals(56, foundOrder.getTotalPrice());
        assertEquals(2, foundOrder.getUser().getId());
    }

    @Test
    public void OrderRepository_Save_ReturnSavedOrder() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Long> perfumeIds = new ArrayList<>();
        perfumeIds.add(5L);
        List<Perfume> perfumeList = perfumeRepository.findByIdIn(perfumeIds);
        User user = userRepository.findByEmail("test123@test.com");
        Order order = new Order();
        order.setAddress("address1");
        order.setCity("city1");
        order.setDate(localDateTime);
        order.setEmail("test1234@test.com");
        order.setFirstName("first_name1");
        order.setLastName("last_name1");
        order.setPhoneNumber("0123456789");
        order.setTotalPrice(27.0);
        order.setPostIndex(1234567890);
        order.setUser(user);
        order.setPerfumes(perfumeList);

        // Act
        Order savedOrder = orderRepository.save(order);

        // Assert
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertEquals("address1", savedOrder.getAddress());
        assertEquals("city1", savedOrder.getCity());
        assertEquals(localDateTime, savedOrder.getDate());
        assertEquals("test1234@test.com", savedOrder.getEmail());
        assertEquals("first_name1", savedOrder.getFirstName());
        assertEquals("last_name1", savedOrder.getLastName());
        assertEquals("0123456789", savedOrder.getPhoneNumber());
        assertEquals(27, savedOrder.getTotalPrice());
        assertEquals("test123@test.com", savedOrder.getUser().getEmail());
        assertEquals(5, savedOrder.getPerfumes().get(0).getId());
    }

    @Test
    public void OrderRepository_SearchUserOrders_ReturnFoundOrders() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Long userId = 2L;
        String searchType = "firstName";
        String text = "first_name";
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Order> foundOrders = orderRepository.searchUserOrders(userId, searchType, text, pageRequest);

        // Assert
        List<Order> list = foundOrders.getContent();

        assertEquals(1, list.size());

        Order order = list.get(0);

        assertNotNull(order);
        assertEquals("address", order.getAddress());
        assertEquals("city", order.getCity());
        assertEquals("2023-11-29T00:00", order.getDate().toString());
        assertEquals("test123@test.com", order.getEmail());
        assertEquals("first_name", order.getFirstName());
        assertEquals("last_name", order.getLastName());
        assertEquals("1234567890", order.getPhoneNumber());
        assertEquals(56, order.getTotalPrice());
        assertEquals(2, order.getUser().getId());

        List<Perfume> perfumeList = order.getPerfumes();

        assertEquals(2, perfumeList.size());
        assertEquals(1, perfumeList.get(0).getId());
        assertEquals(2, perfumeList.get(1).getId());
    }
}