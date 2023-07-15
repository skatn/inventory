package com.namsu.inventoryspring.domain.stockmovement.dao;

import com.namsu.inventoryspring.domain.item.domain.Item;
import com.namsu.inventoryspring.domain.stockmovement.domain.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    Page<StockMovement> findByItem(Item item, Pageable pageable);
}
