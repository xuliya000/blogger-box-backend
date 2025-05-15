package com.dauphine.blogger.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> getAll(@RequestParam(required = false) String value) {
        if (value == null || value.isBlank()) {
            return service.getAll();
        } else {
            return service.getAllByTitleOrContent(value);
        }
    }
}
