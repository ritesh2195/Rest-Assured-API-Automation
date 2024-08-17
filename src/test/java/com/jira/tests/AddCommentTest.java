package com.jira.tests;

import com.jira.constants.APIHttpStatus;
import com.jira.pojo.Comment;
import com.jira.utils.APIUtil;
import com.jira.api.IssueAPI;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

@RunWith(SerenityRunner.class)
public class AddCommentTest {

    @Steps
    IssueAPI issueAPI;

    static String commentId;
    static String id;
    static Comment payload;

    @Order(1)
    @Test
    public void addComment() throws IOException {

        payload = new Comment();

        payload.setBody("add comment");

        String responseBody = issueAPI
                .addComment(payload,"10220")
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.CREATED_201.getCode()))
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        commentId = jsonPath.getString("id");

        String commentText = jsonPath.getString("body");

        assertThat(commentText,equalTo(payload.getBody()));

        String getRequestBody = issueAPI
                .getIssueDetails("10220")
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .extract()
                .body()
                .asString();

        JsonPath jsonPath1 = new JsonPath(getRequestBody);

        int commentCount = jsonPath1.getInt("fields.comment.comments.size()");

        assertThat(jsonPath1.getString("fields.comment.comments["+(commentCount-1)+"].body")
                ,equalTo(payload.getBody()));
    }

    @Order(2)
    @Test
    public void updateComment(){

        System.out.println("Updated comment"+commentId);

        payload.setBody("updated comment");

        String body = issueAPI
                .updateComment("10220",commentId,payload)
                .then()
                .log()
                .all()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .extract()
                .body()
                .asString();

        issueAPI
                .getIssueDetails("10220")
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .assertThat()
                .body("fields.comment.comments[0].body",equalTo(payload.getBody()));

    }
}
