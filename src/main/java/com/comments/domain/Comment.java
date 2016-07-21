package com.comments.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "comment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="comment_text")
    private String commentText;

    @Column(name="city_name")
    private String cityName;

    @Column(name="latitude")
    private Float latitude;

    @Column(name="longitude")
    private Float longitude;

    @Column(name="temperature")
    private Float temperature;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Column(name = "updated", nullable = false)
    private Timestamp updated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    @JsonManagedReference
    private List<Comment> commentReplies;


    // Constructors ---------------------------------------------------------------------------------------- Constructors


    public Comment() {
    }

    public Comment(String name, String commentText, String cityName, Float latitude, Float longitude) {
        this.name = name;
        this.commentText = commentText;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }


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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Timestamp getCreated() {
        return new Timestamp(created.getTime());
    }

    public Timestamp getUpdated() {
        return new Timestamp(updated.getTime());
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
