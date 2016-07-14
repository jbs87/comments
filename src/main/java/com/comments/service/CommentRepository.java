package com.comments.service;

import com.comments.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;



import java.util.List;

/**
 * Created by joshua on 2016-07-11.
 */
@Component
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT c FROM comment c WHERE c.parentComment is null")
    List<Comment> findAllUnique();
}
