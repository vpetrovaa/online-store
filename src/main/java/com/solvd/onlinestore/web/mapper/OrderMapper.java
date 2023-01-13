package com.solvd.onlinestore.web.mapper;

import com.solvd.onlinestore.domain.Order;
import com.solvd.onlinestore.web.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto entityToDto(Order order);

    Order dtoToEntity(OrderDto orderDto);

    List<OrderDto> entityToDto(List<Order> orders);

}