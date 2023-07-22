package com.namsu.inventoryspring.domain.item.api;

import com.namsu.inventoryspring.domain.category.application.CategoryService;
import com.namsu.inventoryspring.domain.category.domain.Category;
import com.namsu.inventoryspring.domain.item.application.ItemService;
import com.namsu.inventoryspring.domain.item.domain.Item;
import com.namsu.inventoryspring.domain.item.dto.ItemUpdateForm;
import com.namsu.inventoryspring.domain.item.dto.ItemsNewForm;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.domain.stockmovement.application.StockMovementService;
import com.namsu.inventoryspring.domain.stockmovement.domain.StockMovement;
import com.namsu.inventoryspring.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final StockMovementService stockMovementService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       Pageable pageable,
                       Model model) {
        Page<Item> items = itemService.getItems(principalDetails.getMember(), pageable);

        model.addAttribute("itemPage", items);
        return "index";
    }

    @GetMapping("/items/new")
    public String addItemForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        List<Category> categories = categoryService.getCategories(principalDetails.getMember());

        model.addAttribute("form", new ItemsNewForm());
        model.addAttribute("categories", categories);

        return "item/addItem";
    }

    @PostMapping("/items/new")
    public String addItem(@AuthenticationPrincipal PrincipalDetails principalDetails,
                          @Validated @ModelAttribute("form") ItemsNewForm form,
                          BindingResult bindingResult,
                          Model model) {

        System.out.println("form = " + form.getCategoryId());
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getCategories(principalDetails.getMember());
            model.addAttribute("categories", categories);

            return "item/addItem";
        }

        Long itemId = itemService.addItem(principalDetails.getMember(), form);

        return "redirect:/items/" + itemId;
    }

    @GetMapping("/items/{itemId}")
    public String item(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @PathVariable("itemId") long itemId,
                       Pageable pageable,
                       Model model) {

        Member member = principalDetails.getMember();
        Item item = itemService.getItem(member, itemId);
        model.addAttribute("item", item);

        Page<StockMovement> movementLogPage = stockMovementService.getLog(item, pageable);
        model.addAttribute("logPage", movementLogPage);

        return "item/item";
    }

    @GetMapping("/items/{itemId}/update")
    public String updateItemForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @PathVariable("itemId") long itemId,
                                 Model model) {

        Item item = itemService.getItem(principalDetails.getMember(), itemId);

        ItemUpdateForm form = new ItemUpdateForm();
        form.setCategoryId(item.getCategory().getId());
        form.setItemName(item.getName());
        form.setUnit(item.getUnit());
        model.addAttribute("form", form);

        List<Category> categories = categoryService.getCategories(principalDetails.getMember());
        model.addAttribute("categories", categories);

        return "item/updateItem";
    }

    @PostMapping("/items/{itemId}/update")
    public String updateItem(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("itemId") long itemId,
                             @Validated @ModelAttribute("form") ItemUpdateForm form,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getCategories(principalDetails.getMember());
            model.addAttribute("categories", categories);

            return "item/updateItem";
        }

        itemService.updateItem(principalDetails.getMember(), itemId, form);
        return "redirect:/items/{itemId}";
    }

    @PostMapping("/items/{itemId}/delete")
    public String deleteItem(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("itemId") long itemId) {

        itemService.deleteItem(principalDetails.getMember(), itemId);
        return "redirect:/";
    }
}
