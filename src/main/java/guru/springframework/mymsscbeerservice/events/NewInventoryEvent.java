package guru.springframework.mymsscbeerservice.events;

import guru.springframework.mymsscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
