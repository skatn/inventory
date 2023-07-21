package com.namsu.inventoryspring.domain.stockmovement.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class StockMovementUpdateForm {

    @Min(0)
    private long quantity;
}
