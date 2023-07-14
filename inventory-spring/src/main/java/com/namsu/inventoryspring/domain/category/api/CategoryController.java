package com.namsu.inventoryspring.domain.category.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping("/category")
    public String category() {
        return "category/category";
    }
}
