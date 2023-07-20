package com.namsu.inventoryspring.domain.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotNull(message = "분류를 선택해주세요")
    private Long categoryId;

    @NotBlank
    private String itemName;

    @NotBlank
    private String unit;
}
