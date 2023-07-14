package com.namsu.inventoryspring.domain.stockmovement.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StockMovementController {

    @GetMapping("/movement/inbound")
    public String inboundForm() {
        return "movement/inbound";
    }

    @GetMapping("/movement/outbound")
    public String outboundForm() {
        return "movement/outbound";
    }

}
