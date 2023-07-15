package com.namsu.inventoryspring.domain.item.application;

import com.namsu.inventoryspring.domain.category.application.CategoryService;
import com.namsu.inventoryspring.domain.item.dao.ItemRepository;
import com.namsu.inventoryspring.domain.item.domain.Item;
import com.namsu.inventoryspring.domain.item.dto.ItemsNewForm;
import com.namsu.inventoryspring.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    public Long addItem(Member member, ItemsNewForm form) {
        Item item = Item.builder()
                .member(member)
                .name(form.getItemName())
                .unit(form.getUnit())
                .quantity(0)
                .category(categoryService.getCategory(form.getCategoryId()))
                .build();

        itemRepository.save(item);

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Item getItem(Member member, Long itemId) {
        return itemRepository.findByIdAndMember(itemId, member).orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems(Member member) {
        return itemRepository.findAllByMember(member);
    }

    @Transactional(readOnly = true)
    public Page<Item> getItems(Member member, Pageable pageable) {
        return itemRepository.findAllByMember(member, pageable);
    }
}
