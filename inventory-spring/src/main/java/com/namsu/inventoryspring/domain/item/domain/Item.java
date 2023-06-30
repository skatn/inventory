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
    private String description;
    private long quantity;
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private Item(String name, String description, long quantity, String unit, Member member, Category category) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.member = member;
        this.category = category;
    }
}
