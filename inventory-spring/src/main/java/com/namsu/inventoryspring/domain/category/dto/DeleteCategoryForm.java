package com.namsu.inventoryspring.domain.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteCategoryForm {

    private Long id;
}
