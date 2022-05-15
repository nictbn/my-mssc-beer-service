package guru.springframework.mymsscbeerservice.services.inventory;

import guru.springframework.mymsscbeerservice.bootstrap.BeerLoader;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {
    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp() {}

    @Test
    void getOnHandInventory() {
        // TODO USE UPC INSTEAD OF UUID
//        Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
//        System.out.println(qoh);
    }
}