package com.namsu.inventoryspring.domain.item.domain;

import com.namsu.inventoryspring.domain.category.domain.Category;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private long quantity;
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private Item(String name, long quantity, String unit, Member member, Category category) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.member = member;
        this.category = category;
    }

    public void addQuantity(long quantity) {
        this.quantity += quantity;
    }

    public void subQuantity(long quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalArgumentException("현재 수량보다 많은 수를 출고할 수 없습니다.");
        }
        this.quantity -= quantity;
    }
}
