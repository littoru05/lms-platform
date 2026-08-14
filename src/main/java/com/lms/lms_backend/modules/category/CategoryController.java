package com.lms.lms_backend.modules.category;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(
                request.getName(),
                request.getSlug(),
                request.getParentId()
        ));
    }

    @Data
    public static class CategoryRequest {
        private String name;
        private String slug;
        private Long parentId;
    }
}