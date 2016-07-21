package com.comments.web;

import com.comments.domain.Comment;
import com.comments.service.CommentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addReplyComment(@RequestParam(required = false) Integer parentId, @RequestParam String name, @RequestParam String commentText,
                                @RequestParam(required = false) String cityName, @RequestParam(required = false) Float latitude,
                                @RequestParam(required = false) Float longitude) throws NotFoundException {
        return commentService.saveComment(name, commentText, parentId, cityName, latitude, longitude);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@RequestParam(required = false) Integer id, @RequestParam String commentText) throws NotFoundException {
        Comment comment = commentService.updateComment(id, commentText);
        if (comment == null) { throw new NotFoundException("Comment not found");}
        return comment;
    }


    @RequestMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getCommentsUnique() {
        return commentService.findAllUnique();
    }

}