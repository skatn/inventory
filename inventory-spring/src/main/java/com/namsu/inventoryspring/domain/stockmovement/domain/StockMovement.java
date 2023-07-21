package com.namsu.inventoryspring.domain.stockmovement.domain;

import com.namsu.inventoryspring.domain.item.domain.Item;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    private StockMovement(Item item, long quantity, MovementType type) {
        this.item = item;
        this.quantity = quantity;
        this.type = type;
    }

    public void changeQuantity(long quantity) {

        if(type == MovementType.INBOUND) {
            if (this.quantity > quantity) {
                item.subQuantity(this.quantity - quantity);
            }
            else {
                item.addQuantity(quantity - this.quantity);
            }
        }
        else {
            if (this.quantity > quantity) {
                item.addQuantity(this.quantity - quantity);
            }
            else {
                item.subQuantity(quantity - this.quantity);
            }
        }


        this.quantity = quantity;
    }
}
