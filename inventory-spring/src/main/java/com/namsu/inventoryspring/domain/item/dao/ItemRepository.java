package com.namsu.inventoryspring.domain.item.dao;

import com.namsu.inventoryspring.domain.item.domain.Item;
import com.namsu.inventoryspring.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByIdAndMember(Long id, Member member);

    List<Item> findAllByMember(Member member);
    Page<Item> findAllByMember(Member member, Pageable pageable);
}
