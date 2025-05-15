package com.dauphine.blogger.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    
    
    private final CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping
    @Operation(
        summary="Get all categories",
        description="Retrieve all categories or filter like name"
    )
    public List<Category> getAll(@RequestParam(required=false) String name){
        List<Category> categories = name == null || name.isBlank()
                ? service.getAll()
                : service.getAllLikeName(name);
        return categories;
    }

    @GetMapping("{id}")
    public Category retrieveCategoryById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public Category createCategory (@RequestBody String name){
        return service.create(name);
    }
    
    @PutMapping("{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody String name){
        return service.updateName(id, name);
    }

    @DeleteMapping("{id}")
    public boolean deleteCategory(@PathVariable UUID id){
        return service.deleteById(id);
    }

}
