package guru.springframework.mymsscbeerservice.services;

import guru.springframework.mymsscbeerservice.web.model.BeerDto;
import guru.springframework.mymsscbeerservice.web.model.BeerPagedList;
import guru.springframework.mymsscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);
}
