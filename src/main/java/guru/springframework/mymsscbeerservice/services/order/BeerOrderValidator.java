package guru.springframework.mymsscbeerservice.services.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.springframework.mymsscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrder) {
        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrder.getBeerOrderLines().forEach(orderLine -> {
            if (beerRepository.findByUpc(orderLine.getUpc()) == null) {
                beersNotFound.incrementAndGet();
            }
        });

        return beersNotFound.get() == 0;
    }
}
