package com.ensaf.haron.belghit.dto.order;

import com.ensaf.haron.belghit.repository.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDto {
    private Integer id;
    private @NotNull Long userId;

    public OrderDto(Order order) {
        this.setId(order.getId());
    }
}
