package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.Basket;
import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.repository.BasketRepository;
import com.solvd.onlinestore.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    @Override
    @Transactional
    public Basket create(Long productId, Long userId) {
        Basket basket = new Basket();
        if (basketRepository.isExistByProductAndUser(productId, userId)) {
            throw new ResourceAlreadyExistsException("You already have this product in your basket, user_id is " + userId);
        }
        basketRepository.create(basket, productId, userId);
        return basket;
    }

    @Override
    @Transactional
    public List<Basket> findAllByUser(Long id) {
        return basketRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        basketRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(Long userId) {
        basketRepository.deleteAllByUserId(userId);
    }

}
