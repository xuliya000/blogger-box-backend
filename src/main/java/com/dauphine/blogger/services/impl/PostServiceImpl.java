package com.dauphine.blogger.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    private final List<Post> temporaryPosts = new ArrayList<>();

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return temporaryPosts.stream()
                .filter(post -> post.getCategoryId().equals(categoryId))
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
        Post post = new Post(UUID.randomUUID(), title, content, categoryId);
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
