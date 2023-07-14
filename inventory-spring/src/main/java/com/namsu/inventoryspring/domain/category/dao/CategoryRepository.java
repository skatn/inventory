package com.namsu.inventoryspring.domain.category.dao;

import com.namsu.inventoryspring.domain.category.domain.Category;
import com.namsu.inventoryspring.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByMember(Member member);
    Optional<Category> findByMemberAndName(Member member, String name);
}
