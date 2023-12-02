package onlineShop.controller;

import onlineShop.constants.ErrorMessage;
import onlineShop.constants.Pages;
import onlineShop.constants.PathConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-perfumes-before.sql", "/sql/create-user-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-orders-after.sql", "/sql/create-user-after.sql", "/sql/create-perfumes-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("test123@test.com")
    public void OrderController_GetOrder_ReturnPageOrder() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        //@WithUserDetails(USER_EMAIL)
        String urlTemplate = PathConstants.ORDER + "/{orderId}";
        Long orderId = 1L;

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate, orderId));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDER))
                .andExpect(model().attribute("order", hasProperty("id", is(orderId))))
                .andExpect(model().attribute("order", hasProperty("totalPrice", is(56.0))))
                .andExpect(model().attribute("order", hasProperty("firstName", is("first_name"))))
                .andExpect(model().attribute("order", hasProperty("lastName", is("last_name"))))
                .andExpect(model().attribute("order", hasProperty("city", is("city"))))
                .andExpect(model().attribute("order", hasProperty("address", is("address"))))
                .andExpect(model().attribute("order", hasProperty("email", is("test123@test.com"))))
                .andExpect(model().attribute("order", hasProperty("phoneNumber", is("1234567890"))))
                .andExpect(model().attribute("order", hasProperty("postIndex", is(1234567890))))
                .andExpect(model().attribute("order", hasProperty("perfumes", hasSize(2))));
    }

    @Test
    @WithUserDetails("test123@test.com")
    public void OrderController_GetOrder_ReturnErrorMessageOrderNotFound() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        //@WithUserDetails(USER_EMAIL)
        String urlTemplate = PathConstants.ORDER + "/{orderId}";
        Long orderId = 2L;

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate, orderId));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.ORDER_NOT_FOUND));
    }

    @Test
    @WithUserDetails("test123@test.com")
    public void OrderController_GetOrdering_ReturnPageOrdering() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        //@WithUserDetails(USER_EMAIL)
        String urlTemplate = PathConstants.ORDER;

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERING))
                .andExpect(model().attribute("perfumes", hasSize(1)));
    }

    @Test
    @WithUserDetails("test123@test.com")
    public void OrderController_GetUserOrdersList_ReturnPageOrders() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        //@WithUserDetails(USER_EMAIL)
        String urlTemplate = PathConstants.ORDER + "/user/orders";

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @WithUserDetails("test123@test.com")
    public void OrderController_PostOrder_ReturnPageFinalizeOrder() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        //@WithUserDetails(USER_EMAIL)
        String urlTemplate = PathConstants.ORDER;
        String firstName = "first_name";
        String lastName = "last_name";
        String city = "city";
        String address = "address";
        String email = "test123@test.com";
        String phoneNumber = "123456789";
        String postIndex = "12345";
        String totalPrice = "35";

        // Act
        ResultActions response = mockMvc.perform(post(urlTemplate)
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("city", city)
                .param("address", address)
                .param("email", email)
                .param("phoneNumber", phoneNumber)
                .param("postIndex", postIndex)
                .param("totalPrice", totalPrice));


        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDER_FINALIZE));
    }

    @Test
    @WithUserDetails("test123@test.com")
    public void OrderController_PostOrder_ReturnErrorMessage() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        //@WithUserDetails(USER_EMAIL)
        String urlTemplate = PathConstants.ORDER;
        String firstName = "";
        String lastName = "";
        String city = "";
        String address = "";
        String email = "";
        String phoneNumber = "";
        String postIndex = "0";
        String totalPrice = "";

        // Act
        ResultActions response = mockMvc.perform(post(urlTemplate)
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("city", city)
                .param("address", address)
                .param("email", email)
                .param("phoneNumber", phoneNumber)
                .param("postIndex", postIndex)
                .param("totalPrice", totalPrice));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERING))
                .andExpect(model().attribute("firstNameError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("lastNameError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("cityError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("addressError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("emailError", is(ErrorMessage.EMAIL_CANNOT_BE_EMPTY)))
                .andExpect(model().attribute("phoneNumberError", is(ErrorMessage.EMPTY_PHONE_NUMBER)))
                .andExpect(model().attribute("postIndexError", is(ErrorMessage.EMPTY_POST_INDEX)));
    }

}