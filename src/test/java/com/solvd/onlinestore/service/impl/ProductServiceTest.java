package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.domain.product.ProductSearchParameter;
import com.solvd.onlinestore.repository.ProductRepository;
import com.solvd.onlinestore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void verifyFindByIdPassedTest() {
        Product product = generateProduct();
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Product productFounded = productService.findById(productId);
        assertEquals(product, productFounded, "Assert that product and productFounded are equals");
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void verifyFindByIdFailedTest() {
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> productService.findById(productId),
                "Assert ResourceDoesNotExistException");
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void verifyFindAllTest() {
        Product product = generateProduct();
        List<Product> products = new ArrayList<>(List.of(product));
        when(productRepository.findAll()).thenReturn(products);
        List<Product> productsFounded = productService.findAll();
        assertEquals(products, productsFounded, "Assert that product and productFounded are equals");
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void verifyCreatePassedTest() {
        Product product = generateProduct();
        String article = "6574537";
        String model = "TR34h";
        Long productId = 1L;
        product.setId(null);
        when(productRepository.isExistByArticleOrModel(anyString(), anyString())).thenReturn(false);
        doAnswer(invocation -> {
            Product productCreated = invocation.getArgument(0);
            productCreated.setId(productId);
            return null;
        }).when(productRepository).create(product);
        product = productService.create(product);
        assertEquals(productId, product.getId(), "Assert that products id are equals");
        verify(productRepository, times(1)).create(product);
        verify(productRepository, times(1)).isExistByArticleOrModel(article, model);
    }

    @Test
    void verifyCreateFailedTest() {
        Product product = generateProduct();
        String article = "6574537";
        String model = "TR34h";
        when(productRepository.isExistByArticleOrModel(anyString(), anyString())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, ()-> productService.create(product),
                "Assert ResourceAlreadyExistsException");
        verify(productRepository, times(1)).isExistByArticleOrModel(article, model);
    }

    @Test
    void verifyDeleteTest() {
        Long productId = 1L;
        productService.delete(productId);
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void verifyFindAllByCategoryTest() {
        Product product = generateProduct();
        List<Product> products = new ArrayList<>(List.of(product));
        String category = "SOFAS";
        when(productRepository.findAllByCategory(anyString())).thenReturn(products);
        List<Product> productsFounded = productService.findAllByCategory(category);
        assertEquals(products, productsFounded, "Assert that product and productFounded are equals");
        verify(productRepository, times(1)).findAllByCategory(category);
    }

    @Test
    void verifyFindAllByCategoryOrderedAscPassedTest() {
        Product product = generateProduct();
        List<Product> products = new ArrayList<>(List.of(product));
        String ordering = "asc";
        String category = "SOFAS";
        when(productRepository.findAllByCategoryOrdered(anyString(), anyString())).thenReturn(products);
        List<Product> productsFounded = productService.findAllByCategoryOrdered(category, ordering);
        assertEquals(products, productsFounded, "Assert that product and productFounded are equals");
        verify(productRepository, times(1)).findAllByCategoryOrdered(category," order by cost " + ordering);
    }

    @Test
    void verifyFindAllByCategoryOrderedDescPassedTest() {
        Product product = generateProduct();
        List<Product> products = new ArrayList<>(List.of(product));
        String ordering = "desc";
        String category = "SOFAS";
        when(productRepository.findAllByCategoryOrdered(anyString(), anyString())).thenReturn(products);
        List<Product> productsFounded = productService.findAllByCategoryOrdered(category, ordering);
        assertEquals(products, productsFounded, "Assert that product and productFounded are equals");
        verify(productRepository, times(1)).findAllByCategoryOrdered(category," order by cost " + ordering);
    }

    @Test
    void verifyFindAllByCategoryOrderedFailedTest() {
        String ordering = "incorrectOrdering";
        String category = "SOFAS";
        assertThrows(SqlException.class, () -> productService.findAllByCategoryOrdered(category, ordering), "Assert SqlException");
        verify(productRepository, times(0)).findAllByCategoryOrdered(category," order by cost " + ordering);
    }


    @Test
    void verifyFindByModelOrArticlePassedTest() {
        Product product = generateProduct();
        when(productRepository.findByModelOrArticle(anyString())).thenReturn(Optional.of(product));
        ProductSearchParameter parameter = new ProductSearchParameter();
        parameter.setParameter("TR34h");
        Product productFounded = productService.findByModelOrArticle(parameter);
        assertEquals(product, productFounded, "Assert that product and productFounded are equals");
        verify(productRepository, times(1)).findByModelOrArticle(product.getModel());
    }

    @Test
    void verifyFindByModelOrArticleFailedTest() {
        Product product = generateProduct();
        when(productRepository.findByModelOrArticle(anyString())).thenReturn(Optional.empty());
        ProductSearchParameter parameter = new ProductSearchParameter();
        parameter.setParameter("TR34h");
        assertThrows(ResourceDoesNotExistException.class, () -> productService.findByModelOrArticle(parameter),
                "Assert ResourceDoesNotExistException");
        verify(productRepository, times(1)).findByModelOrArticle(product.getModel());
    }

    @Test
    void verifyUpdateAmountTest() {
        Product product = generateProduct();
        int amount = 100;
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        productService.updateAmount(product.getId(), amount);
        verify(productRepository, times(1)).updateAmount(product);
        verify(productRepository, times(1)).findById(productId);
    }

    private Product generateProduct() {
        return new Product(1L, Product.Category.SOFAS, "6574537",
                "TR34h", "New interesting excellent confident delicious blue big sofa",
                BigDecimal.valueOf(1356.99), "Green Street, 7", 10);
    }

}