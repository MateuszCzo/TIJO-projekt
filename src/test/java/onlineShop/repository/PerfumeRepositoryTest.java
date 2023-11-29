package onlineShop.repository;

import onlineShop.domain.Perfume;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-perfumes-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PerfumeRepositoryTest {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Test
    public void PerfumeRepository_SaveAll_ReturnSavedPerfume() {
        // Arrange
        Perfume perfume = new Perfume();
        perfume.setPerfumeTitle("Chanel No. 5");
        perfume.setPerfumer("Ernest Beaux");
        perfume.setYear(1921);
        perfume.setCountry("France");
        perfume.setPerfumeGender("Female");
        perfume.setFragranceTopNotes("Aldehydes, Bergamot, Lemon");
        perfume.setFragranceMiddleNotes("Jasmine, Rose, Lily of the Valley");
        perfume.setFragranceBaseNotes("Civet, Amber, Vanilla");
        perfume.setDescription("Chanel No. 5 is a classic floral-aldehyde fragrance.");
        perfume.setFilename("chanel_no_5.jpg");
        perfume.setPrice(100);
        perfume.setVolume("50ml");
        perfume.setType("Eau de Parfum");

        // Act
        Perfume savedPerfume = perfumeRepository.save(perfume);

        // Assert
        assertNotNull(savedPerfume);
        assertNotNull(savedPerfume.getId());
        assertEquals("Chanel No. 5", savedPerfume.getPerfumeTitle());
        assertEquals("Ernest Beaux", savedPerfume.getPerfumer());
        assertEquals(1921, savedPerfume.getYear());
        assertEquals("France", savedPerfume.getCountry());
        assertEquals("Female", savedPerfume.getPerfumeGender());
        assertEquals("Aldehydes, Bergamot, Lemon", savedPerfume.getFragranceTopNotes());
        assertEquals("Jasmine, Rose, Lily of the Valley", savedPerfume.getFragranceMiddleNotes());
        assertEquals("Civet, Amber, Vanilla", savedPerfume.getFragranceBaseNotes());
        assertEquals("Chanel No. 5 is a classic floral-aldehyde fragrance.", savedPerfume.getDescription());
        assertEquals("chanel_no_5.jpg", savedPerfume.getFilename());
        assertEquals(100, savedPerfume.getPrice());
        assertEquals("50ml", savedPerfume.getVolume());
        assertEquals("Eau de Parfum", savedPerfume.getType());
    }

    @Test
    public void PerfumeRepository_FindByIdIn_ReturnFoundPerfumes() {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        List<Long> perfumeIds = new ArrayList<>();
        perfumeIds.add(Long.valueOf(5));
        perfumeIds.add(Long.valueOf(30));
        perfumeIds.add(Long.valueOf(35));
        perfumeIds.add(Long.valueOf(40));

        // Act
        List<Perfume> foundPerfumes = perfumeRepository.findByIdIn(perfumeIds);

        // Assert
        assertNotNull(foundPerfumes);
        assertEquals(4, foundPerfumes.size());
        assertEquals(5, foundPerfumes.get(0).getId());
        assertEquals(30, foundPerfumes.get(1).getId());
        assertEquals(35, foundPerfumes.get(2).getId());
        assertEquals(40, foundPerfumes.get(3).getId());
    }

    @Test
    public void PerfumeRepository_FindAllByOrderByPriceAsc_ReturnFoundPerfumes() {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        Pageable pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Perfume> sortedPerfumes = perfumeRepository.findAllByOrderByPriceAsc(pageRequest);

        // Assert
        assertNotNull(sortedPerfumes);
        assertEquals(3, sortedPerfumes.getTotalPages());
        assertEquals(27, sortedPerfumes.getTotalElements());

        List<Perfume> list = sortedPerfumes.getContent();

        assertEquals(10, list.size());
        for (int i = 0; i < list.size() - 1; i++) {
            Perfume currentPerfume = list.get(i);
            Perfume nextPerfume = list.get(i + 1);

            assertTrue(currentPerfume.getPrice() <= nextPerfume.getPrice());
        }
    }

    @Test
    public void PerfumeRepository_SearchPerfumes_ReturnMatchingPerfumes() {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        String searchType = "perfumeTitle";
        String text = "Chanel";
        PageRequest pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Perfume> matchingPerfumes = perfumeRepository.searchPerfumes(searchType, text, pageRequest);

        // Assert
        List<Perfume> list = matchingPerfumes.getContent();

        assertEquals(2, list.size());
        for (Perfume perfume : list) {
            assertTrue(perfume.getPerfumeTitle().contains(text));
        }
    }

    @Test
    public void PerfumeRepository_GetPerfumesByFilterParams_ReturnFilteredPerfumes() {
        // Arrange
        //@Sql(value = {"/sql/create-perfumes-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        List<String> perfumers = new ArrayList<>();
        perfumers.add("Chanel");
        List<String> genders = new ArrayList<>();
        genders.add("female");
        Integer priceStart = 0;
        Integer priceEnd = 180;
        PageRequest pageRequest = PageRequest.of(0, 10);

        // Act
        Page<Perfume> filteredPerfumes = perfumeRepository.getPerfumesByFilterParams(perfumers, genders, priceStart, priceEnd, pageRequest);

        // Assert
        List<Perfume> list = filteredPerfumes.getContent();

        assertNotNull(filteredPerfumes);
        assertEquals(2, list.size());
        for (Perfume perfume : list) {
            assertTrue(perfumers.contains(perfume.getPerfumer()));
            assertTrue(genders.contains(perfume.getPerfumeGender()));
            assertTrue(perfume.getPrice() >= priceStart && perfume.getPrice() <= priceEnd);
        }
    }
}