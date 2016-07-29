package com.comments.service;

import com.comments.CommentsAApplication;
import com.comments.domain.Comment;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by joshua on 2016-07-19.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CommentsAApplication.class)
@IntegrationTest
public class CommentServiceIT {

    private CommentService commentService;

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Test
    public void saveCompleteCommentNoParentTest() throws NotFoundException {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 45.66)).thenReturn((float) 85.00);
        Comment comment = commentService.saveComment("Joshua", "Hello world", null, "Toronto", (float) 23.44, (float) 45.66);
        checkSavedComment(comment.getId(), "Joshua", "Hello world", null, "Toronto", (float) 85.00, (float) 23.44, (float) 45.66);
    }

    @Test
    public void saveCompleteCommentParentTest() throws NotFoundException {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 45.66)).thenReturn((float) 85.00);
        Comment parentComment = commentService.saveComment("Joshua", "Hello world", null, "Toronto", (float) 23.44, (float) 45.66);
        Comment comment = commentService.saveComment("Joshua", "Hello world", parentComment.getId(), "Toronto", (float) 23.44, (float) 45.66);
        checkSavedComment(comment.getId(), "Joshua", "Hello world", parentComment.getId(), "Toronto", (float) 85.00, (float) 23.44, (float) 45.66);

    }

    @Test
    public void updateCommentTest() throws NotFoundException {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 45.66)).thenReturn((float) 85.00);
        Comment parentComment = commentService.saveComment("Joshua", "Hello world", null, "Toronto", (float) 23.44, (float) 45.66);
        Comment comment = commentService.saveComment("Joshua", "Hello world", parentComment.getId(), "Toronto", (float) 23.44, (float) 45.66);
        commentService.updateComment(comment.getId(), "Hello world modified");
        checkSavedComment(comment.getId(), "Joshua", "Hello world modified", parentComment.getId(), "Toronto", (float) 85.00, (float) 23.44, (float) 45.66);

    }

    @Test
    public void saveCommentNoLocationTest() throws NotFoundException {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 45.66)).thenReturn((float) 85.00);
        Comment comment = commentService.saveComment("Joshua", "Hello world", null, null, null, null);
        checkSavedComment(comment.getId(), "Joshua", "Hello world", null, null, null, null, null);
    }

    private void checkSavedComment(Integer commentId, String name, String commentText, Integer parentCommentId, String cityName, Float temperature, Float latitude, Float longitude){
        Comment comment = commentService.findOne(commentId);
        assertThat(comment.getName(), is(name));
        assertThat(comment.getCommentText(), is(commentText));
        if (comment.getParentComment() != null) {
            assertThat(comment.getParentComment().getId(), is(parentCommentId));
        }
        assertThat(comment.getCityName(), is(cityName));
        assertThat(comment.getTemperature(), is(temperature));
        assertThat(comment.getLatitude(), is(latitude));
        assertThat(comment.getLongitude(), is(longitude));
    }
}
