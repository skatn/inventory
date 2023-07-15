package com.namsu.inventoryspring.domain.stockmovement.api;

import com.namsu.inventoryspring.domain.item.application.ItemService;
import com.namsu.inventoryspring.domain.item.domain.Item;
import com.namsu.inventoryspring.domain.stockmovement.application.StockMovementService;
import com.namsu.inventoryspring.domain.stockmovement.dto.InboundForm;
import com.namsu.inventoryspring.global.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockMovementController {

    private final ItemService itemService;
    private final StockMovementService stockMovementService;

    @GetMapping("/movement/inbound")
    public String inboundForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model) {
        List<Item> items = itemService.getAllItems(principalDetails.getMember());
        model.addAttribute("items", items);
        model.addAttribute("form", new InboundForm());

        return "movement/inbound";
    }

    @PostMapping("/movement/inbound")
    public String inbound(@AuthenticationPrincipal PrincipalDetails principalDetails,
                          @Validated @ModelAttribute("form") InboundForm form,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            List<Item> items = itemService.getAllItems(principalDetails.getMember());
            model.addAttribute("items", items);

            return "movement/inbound";
        }

        stockMovementService.inbound(principalDetails.getMember(), form);

        return "redirect:/";
    }

    @GetMapping("/movement/outbound")
    public String outboundForm() {
        return "movement/outbound";
    }

    @GetMapping("/movement")
    public String movementLog() {
        return "movement/movementLog";
    }
}
