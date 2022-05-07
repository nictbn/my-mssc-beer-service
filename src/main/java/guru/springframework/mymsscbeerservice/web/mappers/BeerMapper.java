package guru.springframework.mymsscbeerservice.web.mappers;

import guru.springframework.mymsscbeerservice.domain.Beer;
import guru.springframework.mymsscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDto beerDto);
}
