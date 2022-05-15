package guru.springframework.mymsscbeerservice.events;

import guru.springframework.mymsscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {
    static final long serialVersionUID = 6015466936529967282L;
    private BeerDto beerDto;
}
