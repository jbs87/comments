package com.comments.service;

import com.comments.domain.Comment;

import java.util.List;

/**
 * Created by joshua on 2016-07-11.
 */
public interface CommentService {
    public List<Comment> findAll();
    public List<Comment> findAllUnique();
    public void saveComment(Comment comment);
    public Comment findOne(int id);
    public void delete(int id);
    public void saveComment(String name, String comment_text, Integer parent_id);
}
