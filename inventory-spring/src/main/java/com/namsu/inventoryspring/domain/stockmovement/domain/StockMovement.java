package com.namsu.inventoryspring.domain.stockmovement.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockMovement {

    @Id
    @GeneratedValue
    @Column(name = "stock_movement_id")
    private Long id;

    private long quantity;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Builder
    private StockMovement(long quantity, LocalDateTime createdDate, MovementType type) {
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.type = type;
    }
}
