package com.namsu.inventoryspring.domain.stockmovement.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class InboundForm {

    @NotNull(message = "제품을 선택해주세요")
    private Long itemId;

    @Min(0)
    private long quantity;
}
