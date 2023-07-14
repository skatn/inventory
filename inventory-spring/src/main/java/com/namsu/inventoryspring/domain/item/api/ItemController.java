package com.namsu.inventoryspring.domain.item.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/item-add")
    public String addItemForm() {
        return "addItem";
    }

    @GetMapping("/items/{itemId}")
    public String item(@PathVariable("itemId") long itemId) {
        return "item/item";
    }

    @GetMapping("/items/{itemId}/update")
    public String updateItemForm(@PathVariable("itemId") long itemId) {
        return "item/updateItem";
    }
}
