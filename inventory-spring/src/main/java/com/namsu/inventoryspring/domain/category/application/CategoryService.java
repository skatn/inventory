package com.namsu.inventoryspring.domain.category.application;

import com.namsu.inventoryspring.domain.category.dao.CategoryRepository;
import com.namsu.inventoryspring.domain.category.domain.Category;
import com.namsu.inventoryspring.domain.category.dto.CreateCategoryForm;
import com.namsu.inventoryspring.domain.category.dto.DeleteCategoryForm;
import com.namsu.inventoryspring.domain.category.dto.UpdateCategoryForm;
import com.namsu.inventoryspring.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long addCategory(Member member, CreateCategoryForm form) {
        Category category = Category.builder()
                .name(form.getName())
                .member(member)
                .build();

        categoryRepository.save(category);

        return category.getId();
    }

    public void updateCategory(Member member, UpdateCategoryForm form) {
        Category category = categoryRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        if (!Objects.equals(category.getMember().getId(), member.getId())) {
            throw new IllegalArgumentException("카테고리 소유자가 일치하지 않습니다.");
        }

        category.changeName(form.getName());
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories(Member member) {
        List<Category> categories = categoryRepository.findAllByMember(member);
        return categories;
    }

    public void deleteCategory(Member member, DeleteCategoryForm form) {
        Category category = categoryRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        if (!Objects.equals(category.getMember().getId(), member.getId())) {
            throw new IllegalArgumentException("카테고리 소유자가 일치하지 않습니다.");
        }

        categoryRepository.delete(category);
    }

}
