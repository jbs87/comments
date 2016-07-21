package com.comments.controller;

import com.comments.CommentsAApplication;
import com.comments.domain.Comment;
import com.comments.service.CommentRepository;
import com.comments.service.CommentService;
import com.comments.service.LocationService;
import io.restassured.RestAssured;
import javassist.NotFoundException;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by joshua on 2016-07-18.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CommentsAApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CommentControllerIT {

    private CommentService commentService;

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

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Value("${local.server.port}")
    int port;

    @Value("${server.ssl.keyAlias}")
    String keystoreFile;

    @Value("${server.ssl.key-store-password}")
    String keystorePass;

    @Rule
    public final ExpectedException exception = ExpectedException.none();



    @Before
    public void setUp() throws Exception {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost";
        RestAssured.keyStore(keystoreFile, keystorePass);
        RestAssured.port = port;
    }

    @Test
    public void canFetchAll() throws Exception {
        populateComments();
        Comment[] comments = given().when().get("/comments/all").as(Comment[].class);
        List<Comment> commentList = new ArrayList<Comment>(Arrays.asList(comments));
        assertThat(commentList.get(0).getName(), is("Jack"));
        assertThat(commentList.get(0).getCityName(), is("Toronto"));
        assertThat(commentList.get(0).getTemperature(), is((float) 85.00));
        assertThat(commentList.get(0).getCommentReplies().get(0).getName(), is("Jill"));
        assertThat(commentList.get(0).getCommentReplies().get(0).getCommentText(), is("First comment reply"));
        assertThat(commentList.get(0).getCommentReplies().get(0).getCityName(), is("LA"));
        assertThat(commentList.get(0).getCommentReplies().get(0).getTemperature(), is((float) 85.00));
        assertThat(commentList.get(1).getName(), is("John"));
        assertThat(commentList.get(1).getCityName(), is("Detroit"));
        assertThat(commentList.get(1).getCommentText(), is("Second comment"));
        assertThat(commentList.get(1).getTemperature(), is((float) 85.00));
    }

    @Test
    public void saveComments() throws Exception {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 90.00)).thenReturn((float) 85.00);
        Comment commentA = given().param("name","Joshua").param("commentText","First post").when().post("/comments/add").as(Comment.class);
        commentA = commentRepository.findOne(commentA.getId());
        assertThat(commentA.getName(), is("Joshua"));
        assertThat(commentA.getCommentText(), is("First post"));
        Comment commentB = given().param("name","Jack").param("commentText","First post reply").param("parentId", commentA.getId()).when().post("/comments/add").as(Comment.class);
        commentB = commentRepository.findOne(commentB.getId());
        assertThat(commentB.getName(), is("Jack"));
        assertThat(commentB.getCommentText(), is("First post reply"));
        assertThat(commentB.getParentComment().getId(), is(commentA.getId()));
        Comment commentC = given().param("name","Jack").param("commentText","First post reply2").param("parentId", commentA.getId()).when().post("/comments/add").as(Comment.class);
        commentC = commentRepository.findOne(commentC.getId());
        assertThat(commentC.getName(), is("Jack"));
        assertThat(commentC.getCommentText(), is("First post reply2"));
        assertThat(commentC.getParentComment().getId(), is(commentA.getId()));
        Comment commentD = given().param("name","John").param("commentText","Second post").param("cityName", "Toronto").param("latitude", (float)23.44).param("longitude", (float) 34).when().post("/comments/add").as(Comment.class);
        commentD = commentRepository.findOne(commentD.getId());
        assertThat(commentD.getName(), is("John"));
        assertThat(commentD.getCommentText(), is("Second post"));
        assertThat(commentD.getParentComment(), is(nullValue()));
    }

    @Test
    public void saveCommentParentNotFound() throws Exception {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 90.00)).thenReturn((float) 85.00);
        Comment commentA = given().param("name","Joshua").param("commentText","First post").when().post("/comments/add").as(Comment.class);
        commentA = commentRepository.findOne(commentA.getId());
        assertThat(commentA.getName(), is("Joshua"));
        assertThat(commentA.getCommentText(), is("First post"));
        try {
            given().param("name", "Jack").param("commentText", "First post reply").param("parentId", 3).when().post("/comments/add").as(Comment.class);
        } catch (Exception e) {
            assertThat(e, instanceOf(NotFoundException.class));
            assertThat(e.getMessage(), is("Parent message not found"));
        }
    }

    @Test
    public void updateComments() throws Exception {
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 90.00)).thenReturn((float) 85.00);
        Comment commentA = given().param("name","Joshua").param("commentText","First post").when().post("/comments/add").as(Comment.class);
        assertThat(commentA.getCreated().equals(commentA.getUpdated()), is(true));
        commentA = given().param("id", commentA.getId()).param("commentText","Changed first post").when().post("/comments/update").as(Comment.class);
        assertThat(commentA.getCommentText(), is("Changed first post"));
        assertThat(commentA.getCreated().equals(commentA.getUpdated()), is(false));
    }

    @Test
    public void updateCommentNotFound() throws NotFoundException{
        RestAssured.useRelaxedHTTPSValidation();
        Mockito.when(locationService.findTemperature((float) 23.44, (float) 90.00)).thenReturn((float) 85.00);
        Comment commentA = given().param("name","Joshua").param("commentText","First post").when().post("/comments/add").as(Comment.class);
        assertThat(commentA.getCreated().equals(commentA.getUpdated()), is(true));
        try {
            given().param("id", 5).param("commentText", "Changed first post").when().post("/comments/update");
        } catch (Exception e){
            assertThat(e, instanceOf(NotFoundException.class));
        }
    }

    private void populateComments() throws Exception {
        Mockito.when(locationService.findTemperature((float) 23.23, (float) 90.00)).thenReturn((float) 85.00);
        commentRepository.deleteAll();
        Comment commentA = commentService.saveComment("Jack","First comment", null, "Toronto", (float) 23.23, (float) 90.00);
        Comment commentB = commentService.saveComment("Jill","First comment reply", commentA.getId(), "LA", (float) 23.23, (float) 90.00);
        Comment commentC = commentService.saveComment("Jack","First comment reply reply", commentB.getId(), "NYC", (float) 23.23, (float) 90.00);
        Comment commentD = commentService.saveComment("John","Second comment", null, "Detroit", (float) 23.23, (float) 90.00);
    }



}