package com.jira.tests;

import com.jira.constants.APIHttpStatus;
import com.jira.utils.FileReaderUtil;
import com.jira.api.IssueAPI;
import com.jira.pojo.CommentPayload;
import com.jira.utils.ExcelUtil;
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
    static ExcelUtil excelReader;
    static String id;
    CommentPayload payload = new CommentPayload();

    @Order(1)
    @Test
    public void addComment() throws IOException {

        excelReader = new ExcelUtil(FileReaderUtil.getInstance().getExcelFilePath());

        id = excelReader.getCellData("AddComment","Id",2);

        String comment = excelReader.getCellData("AddComment","Comment",2);

        String responseBody = issueAPI.addComment(payload.payLoad(),"10220").then().assertThat()

                .statusCode(APIHttpStatus.CREATED_201.getCode()).extract().body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        commentId = jsonPath.getString("id");

        String commentText = jsonPath.getString("body");

        assertThat(commentText,equalTo(comment));

        String getRequestBody = issueAPI.getIssueDetails("10220").then().assertThat().statusCode(200).extract()

                .body().asString();

        JsonPath jsonPath1 = new JsonPath(getRequestBody);

        int commentCount = jsonPath1.getInt("fields.comment.comments.size()");

        assertThat(jsonPath1.getString("fields.comment.comments["+(commentCount-1)+"].body"),equalTo(comment));
    }

    @Order(2)
    @Test
    public void updateComment(){

        String updatedComment = excelReader.getCellData("AddComment","UpdatedComment",2);

        String body = issueAPI.updateComment(id,commentId,payload.updateCommentPayload()).then()

                .assertThat().statusCode(APIHttpStatus.OK_200.getCode()).extract().body().asString();

        issueAPI.getIssueDetails(id).then().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).assertThat()

                .body("fields.comment.comments[0].body",equalTo(updatedComment));

    }
}
