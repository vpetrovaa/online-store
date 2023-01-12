package com.solvd.onlinestore.web.mapper.product;

import com.solvd.onlinestore.domain.product.ProductSearchParameter;
import com.solvd.onlinestore.web.dto.product.ProductSearchParameterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSearchParameterMapper {

    ProductSearchParameter parameterDtoToParameter(ProductSearchParameterDto parameterDto);

}