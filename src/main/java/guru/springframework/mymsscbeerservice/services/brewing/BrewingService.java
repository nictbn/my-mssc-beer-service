package guru.springframework.mymsscbeerservice.services.brewing;

import guru.springframework.mymsscbeerservice.config.JmsConfig;
import guru.springframework.mymsscbeerservice.domain.Beer;
import guru.sfg.common.events.BrewBeerEvent;
import guru.springframework.mymsscbeerservice.repositories.BeerRepository;
import guru.springframework.mymsscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.mymsscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer inventoryQuantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());
            log.debug("Min On Hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + inventoryQuantityOnHand);

            if (beer.getMinOnHand() >= inventoryQuantityOnHand) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
