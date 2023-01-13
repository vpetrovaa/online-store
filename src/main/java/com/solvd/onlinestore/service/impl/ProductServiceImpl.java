package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.product.ProductSearchParameter;
import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.repository.ProductRepository;
import com.solvd.onlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("There are no product with id" + id));
        return product;
    }

    @Override
    @Transactional
    public List<Product> findAll(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    @Transactional
    public Product save(Product product){
        if(productRepository.isExistByArticleOrModel(product.getArticle(), product.getModel())) {
            throw new ResourceAlreadyExistsException("Such product is already exists, article - " + product.getArticle());
        }
        productRepository.save(product);
        return product;
    }

    @Override
    @Transactional
    public void delete(Long id){
        productRepository.delete(id);
    }

    @Override
    @Transactional
    public List<Product> findAllByCategory(String category){
        List<Product> products = productRepository.findAllByCategory(category);
        return products;
    }

    @Override
    @Transactional
    public List<Product> findAllByCategoryOrdered(String category, String ordering){
        ordering = switch (ordering){
            case "asc" -> " order by cost asc";
            case "desc" -> " order by cost desc";
            default -> throw new SqlException("Exception in ordering title(asc or desc only) ");
        };
        List<Product> products = productRepository.findAllByCategoryOrdered(category, ordering);;
        return products;
    }

    @Override
    @Transactional
    public Product findByModelOrArticle(ProductSearchParameter parameter){
        Product product = productRepository.findByModelOrArticle(parameter.getParameter())
                .orElseThrow(() -> new ResourceDoesNotExistException("There are " +
                        "no product with such article or/and model - " + parameter.getParameter()));
        return product;
    }

    @Override
    @Transactional
    public void updateAmount(Long id, int amount) {
        Product product = findById(id);
        product.setAmount(product.getAmount() + amount);
        productRepository.updateAmount(product);
    }

}
