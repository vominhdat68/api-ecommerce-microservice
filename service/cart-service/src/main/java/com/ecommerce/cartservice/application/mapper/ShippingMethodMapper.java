package com.ecommerce.cartservice.application.mapper;

import com.ecommerce.cartservice.application.dto.response.ShippingMethodResponse;
import com.ecommerce.cartservice.domain.model.ShippingMethod;
import com.ecommerce.cartservice.infrastructure.entity.ShippingMethodEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShippingMethodMapper {

    public static ShippingMethod toResponseList(ShippingMethodEntity method) {
        return  new ShippingMethod(
                method.getCode(),
                method.getName(),
                method.getFee());
    }


}

