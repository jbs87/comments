package com.comments.service;

import com.comments.domain.Comment;
import javassist.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Created by joshua on 2016-07-11.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

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

    public Comment saveComment(String name, String comment_text, Integer parent_id, String cityName, Float latitude, Float longitude) throws NotFoundException {
        Comment comment = new Comment();
        comment.setName(name);
        comment.setCommentText(comment_text);
        comment.setCityName(cityName);
        comment.setCreated(Timestamp.from(Instant.now()));
        comment.setUpdated(Timestamp.from(Instant.now()));
        if (latitude!= null && longitude != null){
            comment.setLatitude(latitude);
            comment.setLongitude(longitude);
            Float temperature = locationService.findTemperature(latitude, longitude);
            comment.setTemperature(temperature);
        }
        if (parent_id != null) {
            Comment parent = findOne(parent_id);
            if (parent != null) {
                comment.setParentComment(parent);
            } else {
                throw new NotFoundException("Parent comment not found");
            }

        }
        return saveComment(comment);
    }

    @Override
    public Comment updateComment(int id, String commentText) throws NotFoundException {
        Comment comment = findOne(id);
        if (comment != null) {
            comment.setCommentText(commentText);
            comment.setCreated(Timestamp.from(Instant.now()));
            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findOne(int id) {
        return commentRepository.findOne(id);
    }

    @Override
    public void delete(int id) {
        commentRepository.delete(id);
    }
}
