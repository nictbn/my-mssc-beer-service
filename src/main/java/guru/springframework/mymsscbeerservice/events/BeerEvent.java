package guru.springframework.mymsscbeerservice.events;

import guru.springframework.mymsscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    static final long serialVersionUID = 6015466936529967282L;
    private final BeerDto beerDto;
}
