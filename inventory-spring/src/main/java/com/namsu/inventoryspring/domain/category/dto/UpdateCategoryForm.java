package com.namsu.inventoryspring.domain.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateCategoryForm {

    @NotBlank
    private String name;

    private Long id;
}
