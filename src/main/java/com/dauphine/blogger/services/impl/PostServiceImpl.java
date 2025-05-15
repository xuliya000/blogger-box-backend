package com.dauphine.blogger.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    private final List<Post> temporaryPosts = new ArrayList<>();

    public PostServiceImpl() {
        // Initialisation optionnelle avec des données fictives
        Category cat1 = new Category(UUID.randomUUID(), "Tech");
        Category cat2 = new Category(UUID.randomUUID(), "Art");

        temporaryPosts.add(new Post("First Post", "This is the first post content", cat1));
        temporaryPosts.add(new Post("Second Post", "This is the second post content", cat2));
        temporaryPosts.add(new Post("Another Post", "Belongs to Tech", cat1));
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return temporaryPosts.stream()
                .filter(post -> post.getCategory().getId().equals(categoryId))
                .toList();
    }

    @Override
    public List<Post> getAll() {
        return temporaryPosts;
    }

    @Override
    public Post getById(UUID id) {
        return temporaryPosts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Post create(String title, String content, UUID categoryId) {
        // Création d'une catégorie fictive (car pas encore connectée à une vraie base)
        Category category = new Category(categoryId, "Temporary category");
        Post post = new Post(title, content, category); // UUID généré dans le constructeur
        temporaryPosts.add(post);
        return post;
    }

    @Override
    public Post update(UUID id, String title, String content) {
        Post post = getById(id);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
        }
        return post;
    }

    @Override
    public boolean deleteById(UUID id) {
        return temporaryPosts.removeIf(post -> post.getId().equals(id));
    }
}
