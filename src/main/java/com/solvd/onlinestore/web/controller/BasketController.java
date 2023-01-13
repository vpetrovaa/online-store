package com.solvd.onlinestore.web.controller;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.service.BasketService;
import com.solvd.onlinestore.web.dto.BasketDto;
import com.solvd.onlinestore.web.mapper.BasketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BasketController {

    private final BasketService basketService;
    private final BasketMapper basketMapper;

    @PostMapping("/users/{userId}/baskets/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BasketDto save(@PathVariable("productId") Long productId, @PathVariable("userId") Long userId){
        BasketDto basketDto = basketMapper.entityToDto(basketService.save(productId, userId));
        return basketDto;
    }

    @DeleteMapping("/users/baskets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id){
        basketService.delete(id);
    }

    @GetMapping("/users/{id}/baskets")
    @ResponseStatus(HttpStatus.OK)
    public List<BasketDto> findAllByUser(@PathVariable(name = "id") Long id){
        List<Basket> baskets = basketService.findAllByUser(id);
        List<BasketDto> basketsDto = basketMapper.entityToDto(baskets);
        return basketsDto;
    }

}
