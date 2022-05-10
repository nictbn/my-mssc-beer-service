package guru.springframework.mymsscbeerservice.services;

import guru.springframework.mymsscbeerservice.domain.Beer;
import guru.springframework.mymsscbeerservice.repositories.BeerRepository;
import guru.springframework.mymsscbeerservice.web.controller.NotFoundException;
import guru.springframework.mymsscbeerservice.web.mappers.BeerMapper;
import guru.springframework.mymsscbeerservice.web.model.BeerDto;
import guru.springframework.mymsscbeerservice.web.model.BeerPagedList;
import guru.springframework.mymsscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = " #showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = " #showInventoryOnHand == false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent().stream()
                            .map(beerMapper::beerToBeerDtoWithInventory)
                            .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements()
            );
        } else {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent().stream()
                            .map(beerMapper::beerToBeerDto)
                            .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements()
            );
        }
        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
