package com.comments.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "comment")
public class Comment implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="name")
    String name;

    @Column(name="comment_text")
    String commentText;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    @JsonManagedReference
    private List<Comment> commentReplies;


    // Constructors ---------------------------------------------------------------------------------------- Constructors

    // Public Methods ------------------------------------------------------------------------------------ Public Methods


    public Integer getId() {
        return id;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getCommentReplies() {
        return commentReplies;
    }

    public void setCommentReplies(List<Comment> commentReplies) {
        this.commentReplies = commentReplies;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
