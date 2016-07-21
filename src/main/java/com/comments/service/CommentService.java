package com.comments.service;

import com.comments.domain.Comment;
import javassist.NotFoundException;

import java.util.List;

/**
 * Created by joshua on 2016-07-11.
 */
public interface CommentService {
    public List<Comment> findAll();
    public List<Comment> findAllUnique();
    public Comment saveComment(Comment comment) throws NotFoundException;
    public Comment updateComment(int id, String commentText) throws NotFoundException;
    public Comment findOne(int id);
    public void delete(int id);
    public Comment saveComment(String name, String comment_text, Integer parent_id, String cityName, Float latitude, Float longitude) throws NotFoundException;
}
