package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> temporaryCategories;

    public CategoryServiceImpl(){
        temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(),"my first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(),"my second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(),"my third category"));
    }

    @Override
    public List<Category> getAll() {
        return temporaryCategories;
    }

    @Override
    public Category getById(UUID id) {
        return temporaryCategories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Category create(String name) {
        Category category = new Category(UUID.randomUUID(), name);
        temporaryCategories.add(category);
        return category;
    }

    @Override
    public Category updateName(UUID id, String newName) {
        Category category = temporaryCategories.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst()
                .orElse(null);
        if(category != null){
            category.setName(newName);
        }
        return category;
    }

    @Override
    public boolean deleteById(UUID id) {
        return temporaryCategories.removeIf(category -> category.getId().equals(id));
    }
    

}
