package com.namsu.inventoryspring.domain.stockmovement.application;

import com.namsu.inventoryspring.domain.item.application.ItemService;
import com.namsu.inventoryspring.domain.item.domain.Item;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.domain.stockmovement.dao.StockMovementRepository;
import com.namsu.inventoryspring.domain.stockmovement.domain.MovementType;
import com.namsu.inventoryspring.domain.stockmovement.domain.StockMovement;
import com.namsu.inventoryspring.domain.stockmovement.dto.InboundForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ItemService itemService;

    @Transactional(readOnly = true)
    public Page<StockMovement> getLog(Item item, Pageable pageable) {
        return stockMovementRepository.findByItem(item, pageable);
    }

    public void inbound(Member member, InboundForm form) {
        Item item = itemService.getItem(member, form.getItemId());
        item.addQuantity(form.getQuantity());

        StockMovement stockMovement = StockMovement.builder()
                .type(MovementType.INBOUND)
                .quantity(form.getQuantity())
                .item(item)
                .build();

        stockMovementRepository.save(stockMovement);
    }

}
