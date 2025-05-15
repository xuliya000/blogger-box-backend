package com.dauphine.blogger.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.PostRepository;
import com.dauphine.blogger.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return postRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllByTitleOrContent(String value) {
        return postRepository.findAllByTitleOrContentLike(value);
    }

    @Override
    public Post getById(UUID id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public Post create(String title, String content, UUID categoryId) {
        Category category = new Category();
        category.setId(categoryId); // On ne récupère pas la catégorie complète ici
        Post post = new Post(title, content, category);
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content) {
        Post post = getById(id);
        if (post == null) return null;
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    @Override
    public boolean deleteById(UUID id) {
        postRepository.deleteById(id);
        return true;
    }
}
