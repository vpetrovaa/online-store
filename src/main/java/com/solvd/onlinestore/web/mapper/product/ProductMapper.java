package com.solvd.onlinestore.web.mapper.product;

import com.solvd.onlinestore.domain.product.Product;
import com.solvd.onlinestore.web.dto.product.ProductDto;
import com.solvd.onlinestore.web.mapper.BasketMapper;
import com.solvd.onlinestore.web.mapper.OrderMapper;
import com.solvd.onlinestore.web.mapper.WarehouseMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BasketMapper.class, OrderMapper.class, WarehouseMapper.class})
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<ProductDto> productsToProductDto(List<Product> products);

}