package onlineShop.controller;

import onlineShop.constants.ErrorMessage;
import onlineShop.constants.Pages;
import onlineShop.constants.PathConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-perfumes-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PerfumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PerfumeController_GetPerfumeById_ReturnPagePerfume() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String urlTemplate = PathConstants.PERFUME + "/{perfumeId}";
        Long perfumeId = 1L;

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate, perfumeId));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUME))
                .andExpect(model().attribute("perfume", hasProperty("id", is(perfumeId))))
                .andExpect(model().attribute("perfume", hasProperty("perfumeTitle", is("Boss Bottled Night"))))
                .andExpect(model().attribute("perfume", hasProperty("perfumer", is("Hugo Boss"))))
                .andExpect(model().attribute("perfume", hasProperty("year", is(2010))))
                .andExpect(model().attribute("perfume", hasProperty("country", is("Germany"))))
                .andExpect(model().attribute("perfume", hasProperty("perfumeGender", is("male"))))
                .andExpect(model().attribute("perfume", hasProperty("fragranceTopNotes", is("Birch leaf, Lavender"))))
                .andExpect(model().attribute("perfume", hasProperty("fragranceMiddleNotes", is("Cardamom, Jasmine, African violet"))))
                .andExpect(model().attribute("perfume", hasProperty("fragranceBaseNotes", is("Salmwood, Musky notes, Sandalwood"))))
                .andExpect(model().attribute("perfume", hasProperty("filename", is("4735a4c8-e1fc-43ce-8ff9-0bd28d00ce20.Hugo Boss Boss Bottled Night.jpg"))))
                .andExpect(model().attribute("perfume", hasProperty("price", is(35))))
                .andExpect(model().attribute("perfume", hasProperty("volume", is("100"))))
                .andExpect(model().attribute("perfume", hasProperty("type", is("Eau de Toilette"))));
    }

    @Test
    public void PerfumeController_GetPerfumeById_ReturnErrorMessagePerfumeNotFound() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String urlTemplate = PathConstants.PERFUME + "/{perfumeId}";
        Long perfumeId = 7L;

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate, perfumeId));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.PERFUME_NOT_FOUND));
    }

    @Test
    public void PerfumeController_GetPerfumesByFilterParams_ReturnPagePerfumes() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String urlTemplate = PathConstants.PERFUME;

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(12))));
    }

    @Test
    public void PerfumeController_GetPerfumesByFilterParams_ReturnPageFoundPerfumes() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String urlTemplate = PathConstants.PERFUME;
        String filterBy1 = "perfumers";
        String perfumer1 = "Creed";
        String perfumer2 = "Dior";
        String filterBy2 = "genders";
        String genders = "male";
        String filterBy3 = "price";
        String price = "60";

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate)
                .param(filterBy1, perfumer1, perfumer2)
                .param(filterBy2, genders)
                .param(filterBy3, price));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(5))));
    }

    @Test
    public void PerfumeController_SearchPerfumes_ByPerfumer_ReturnPageFoundPerfumes() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String urlTemplate = PathConstants.PERFUME + "/search";
        String searchType = "searchType";
        String perfumer = "perfumer";
        String text = "text";
        String creed = "Creed";

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate)
                .param(searchType, perfumer)
                .param(text, creed));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(7))));
    }

    @Test
    public void PerfumeController_SearchPerfumes_ByPerfumeTitle_ReturnPageFoundPerfumes() throws Exception {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String urlTemplate = PathConstants.PERFUME + "/search";
        String searchType = "searchType";
        String perfumeTitle = "perfumeTitle";
        String text = "text";
        String aventus = "Aventus";

        // Act
        ResultActions response = mockMvc.perform(get(urlTemplate)
                .param(searchType, perfumeTitle)
                .param(text, aventus));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(view().name(Pages.PERFUMES))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

}