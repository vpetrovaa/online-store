package com.solvd.onlinestore.web.mapper;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.web.dto.BasketDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface BasketMapper {

    BasketDto basketToBasketDto(Basket basket);

    List<BasketDto> basketsToBasketDto(List<Basket> baskets);

}
