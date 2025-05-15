package com.dauphine.blogger.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dauphine.blogger.models.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByCategoryId(UUID categoryId);

    @Query("""
        SELECT p
        FROM Post p
        WHERE UPPER(p.title) LIKE UPPER(CONCAT('%', :value, '%'))
           OR UPPER(p.content) LIKE UPPER(CONCAT('%', :value, '%'))
    """)
    List<Post> findAllByTitleOrContentLike(@Param("value") String value);
}