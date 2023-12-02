package onlineShop.service;

import onlineShop.domain.Perfume;
import onlineShop.dto.request.SearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-orders-after.sql", "/sql/create-user-after.sql", "/sql/create-perfumes-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

class PerfumeServiceTest {

    @Autowired
    private PerfumeService perfumeService;


    @Test
    public void PerfumeService_GetPerfumeById_ReturnFoundPerfume() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Long perfumeId = 1L;

        // Act
        Perfume foundPerfume = perfumeService.getPerfumeById(perfumeId);

        // Assert
        assertNotNull(foundPerfume);
        assertEquals(perfumeId, foundPerfume.getId());
    }

    @Test
    public void PerfumeService_GetPopularPerfumes_ReturnPerfumesList() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        List<Long> perfumeIds = Arrays.asList(26L, 43L, 46L, 106L, 34L, 76L, 82L, 85L, 27L, 39L, 79L, 86L);

        // Act
        List<Perfume> popularPerfumes = perfumeService.getPopularPerfumes();

        // Assert
        assertNotNull(popularPerfumes);
        for (Perfume perfume : popularPerfumes) {
            assertTrue(perfumeIds.contains(perfume.getId()));
        }
    }

    @Test
    public void PerfumeService_GetPerfumesByFilterParams_ReturnPerfumePage() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        SearchRequest searchRequest = createSearchRequest();
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Perfume> perfumePage = perfumeService.getPerfumesByFilterParams(searchRequest, pageRequest);

        // Assert
        assertNotNull(perfumePage);
        List<Perfume> perfumeList = perfumePage.toList();
        assertEquals(3, perfumeList.size());
    }

    private SearchRequest createSearchRequest() {
        SearchRequest searchRequest = new SearchRequest();
        List<String> perfumeList = new ArrayList<>();
        perfumeList.add("Hugo Boss");
        searchRequest.setPerfumers(perfumeList);
        List<String> genderList = new ArrayList<>();
        genderList.add("male");
        searchRequest.setGenders(genderList);
        searchRequest.setPrice(0);
        searchRequest.setSearchType("perfumeTitle");
        searchRequest.setText("Boss Bottled Night");
        return searchRequest;
    }

    @Test
    public void PerfumeService_SearchPerfumes_ReturnPerfumePage() {
        // Arrange
        //@Sql(value = {"/sql/create-user-before.sql", "/sql/create-perfumes-before.sql", "/sql/create-orders-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        SearchRequest searchRequest = createSearchRequest();
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Perfume> perfumePage = perfumeService.searchPerfumes(searchRequest, pageRequest);

        // Assert
        assertNotNull(perfumePage);
        List<Perfume> perfumeList = perfumePage.toList();
        assertEquals(1, perfumeList.size());
    }

}