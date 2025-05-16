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

import com.dauphine.blogger.exceptions.PostNotFoundException;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all posts", description = "Returns all posts or filters by title/content")
    public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) String value) {
        List<Post> posts = (value == null || value.isBlank())
                ? service.getAll()
                : service.getAllByTitleOrContent(value);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID")
    public ResponseEntity<Post> getById(@PathVariable UUID id) {
        Post post = service.getById(id);
        if (post == null) {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get all posts by category ID")
    public ResponseEntity<List<Post>> getByCategoryId(@PathVariable UUID categoryId) {
        List<Post> posts = service.getAllByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    @Operation(summary = "Create a new post")
    public ResponseEntity<Post> create(@Valid @RequestBody PostRequest request) {
        Post created = service.create(request.getTitle(), request.getContent(), request.getCategoryId());
        return ResponseEntity.created(URI.create("/v1/posts/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a post by ID")
    public ResponseEntity<Post> update(@PathVariable UUID id, @RequestBody PostRequest request) {
        Post updated = service.update(id, request.getTitle(), request.getContent());
        if (updated == null) {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post by ID")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = service.deleteById(id);
        if (!deleted) {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
