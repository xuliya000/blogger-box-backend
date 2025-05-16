package com.dauphine.blogger.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dauphine.blogger.exceptions.CategoryNotFoundException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all categories or filter by name")
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = (name == null || name.isBlank())
                ? service.getAll()
                : service.getAllLikeName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<Category> getById(@PathVariable UUID id) {
        Category category = service.getById(id);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category created = service.create(request.getName());
        return ResponseEntity.created(URI.create("/v1/categories/" + created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update category name by ID")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody String name) {
        Category updated = service.updateName(id, name);
        if (updated == null) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a category by ID")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        boolean deleted = service.deleteById(id);
        if (!deleted) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
