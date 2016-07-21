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
        assertThat(comment.getName(), is("Joshua"));
        assertThat(comment.getCommentText(), is("Hello world"));
        assertThat(comment.getParentComment(), is(nullValue()));
        assertThat(comment.getCityName(), is("Toronto"));
        assertThat(comment.getTemperature(), is((float) 85.00));
        assertThat(comment.getLatitude(), is((float) 23.44));
        assertThat(comment.getLongitude(), is((float) 45.66));
    }

    @Test
    public void saveCompleteCommentParentTest() throws NotFoundException {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 45.66)).thenReturn((float) 85.00);
        Comment parentComment = commentService.saveComment("Joshua", "Hello world", null, "Toronto", (float) 23.44, (float) 45.66);
        Comment comment = commentService.saveComment("Joshua", "Hello world", parentComment.getId(), "Toronto", (float) 23.44, (float) 45.66);
        assertThat(comment.getName(), is("Joshua"));
        assertThat(comment.getCommentText(), is("Hello world"));
        assertThat(comment.getParentComment().getId(), is(parentComment.getId()));
        assertThat(comment.getCityName(), is("Toronto"));
        assertThat(comment.getTemperature(), is((float) 85.00));
        assertThat(comment.getLatitude(), is((float) 23.44));
        assertThat(comment.getLongitude(), is((float) 45.66));
    }

    @Test
    public void saveCommentNoLocationTest() throws NotFoundException {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 45.66)).thenReturn((float) 85.00);
        Comment comment = commentService.saveComment("Joshua", "Hello world", null, null, null, null);
        assertThat(comment.getName(), is("Joshua"));
        assertThat(comment.getCommentText(), is("Hello world"));
        assertThat(comment.getParentComment(), is(nullValue()));
        assertThat(comment.getCityName(), is(nullValue()));
        assertThat(comment.getTemperature(), is(nullValue()));
        assertThat(comment.getLatitude(), is(nullValue()));
        assertThat(comment.getLongitude(), is(nullValue()));
    }
}
