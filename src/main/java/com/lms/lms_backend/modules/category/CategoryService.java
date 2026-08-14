package com.lms.lms_backend.modules.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(String name, String slug, Long parentId) {
        if (categoryRepository.existsBySlug(slug)) {
            throw new RuntimeException("Category slug đã tồn tại!");
        }

        Category parent = null;
        if (parentId != null) {
            parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Category cha không tồn tại!"));
        }

        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .parent(parent)
                .build();

        return categoryRepository.save(category);
    }
}