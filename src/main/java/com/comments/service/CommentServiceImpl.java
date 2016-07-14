package com.comments.service;

import com.comments.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joshua on 2016-07-11.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findAllUnique() {
        return commentRepository.findAllUnique();
    }

    public void saveComment(String name, String comment_text, Integer parent_id){
        Comment comment = new Comment();
        comment.setName(name);
        comment.setCommentText(comment_text);
        if (parent_id != null) {
            System.out.println("ID: "+parent_id);
            Comment parent = findOne(parent_id);
            comment.setParentComment(parent);

        }
        saveComment(comment);
    }


    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findOne(int id) {
        System.out.println("ID: "+id);
        return commentRepository.findOne(id);
    }

    @Override
    public void delete(int id) {
        commentRepository.delete(id);
    }
}
