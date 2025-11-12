package com.ecommerce.cartservice.application.mapper;


import com.ecommerce.cartservice.application.dto.response.CartItemResponse;
import com.ecommerce.cartservice.application.dto.response.CartResponse;
import com.ecommerce.cartservice.application.dto.response.EstimatedCostResponse;
import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.cartservice.domain.model.EstimatedCost;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstimatedCostMapper {

    public static EstimatedCostResponse toResponse(EstimatedCost estimatedCost) {


        return EstimatedCostResponse.builder()

                .build();
    }


}

