package com.comments.web;

import com.comments.domain.Comment;
import com.comments.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by joshua on 2016-07-11.
 */
@RestController
@RequestMapping(value = "/comments")
public class CommentsController {


    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/add/{name}/{comment_text}/{parent_id}", method = RequestMethod.POST)
    public void addReplyComment(@PathVariable String name, @PathVariable String comment_text,
                                 @PathVariable int parent_id) {
        commentService.saveComment(name, comment_text, parent_id);
    }

    @RequestMapping(value = "/add/{name}/{comment_text}", method = RequestMethod.POST)
    public void addComment(@PathVariable String name, @PathVariable String comment_text) {
        commentService.saveComment(name, comment_text, null);
    }


    @RequestMapping(value = "/all")
    public List<Comment> getComments() {
        return commentService.findAll();
    }

    @RequestMapping(value = "/allunique")
    public List<Comment> getCommentsUnique() {
        return commentService.findAllUnique();
    }

    @RequestMapping(value = "/{id}")
    public Comment getComment(@PathVariable int id) {
        return commentService.findOne(id);
    }


}