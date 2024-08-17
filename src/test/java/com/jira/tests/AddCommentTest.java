package com.jira.tests;

import com.jira.builders.JiraIssueBuilder;
import com.jira.constants.APIHttpStatus;
import com.jira.pojo.Comment;
import com.jira.pojo.Payload;
import com.jira.utils.APIUtil;
import com.jira.api.IssueAPI;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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

    @Before
    public void setUp(){
        if(id==null){
            Payload issuePayload = new JiraIssueBuilder()
                    .setProjectKey("RP")
                    .setSummary("Feature is not working as expected")
                    .setDescription("Creating an issue using project keys and issue type names using the REST API")
                    .setIssueTypeName("Bug")
                    .build();

            String responseBody = issueAPI
                    .createIssue(issuePayload)
                    .then()
                    .extract()
                    .body()
                    .asString();

            JsonPath jsonPath = new JsonPath(responseBody);

            id = jsonPath.getString("id");
        }

    }

    @Order(1)
    @Test
    public void addComment() throws IOException {

        System.out.println("id is "+id);

        payload = new Comment();

        payload.setBody("add comment");

        String responseBody = issueAPI
                .addComment(payload,id)
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
                .getIssueDetails(id)
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
                .updateComment(id,commentId,payload)
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .extract()
                .body()
                .asString();

        issueAPI
                .getIssueDetails(id)
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .assertThat()
                .body("fields.comment.comments[0].body",equalTo(payload.getBody()));

    }
}
