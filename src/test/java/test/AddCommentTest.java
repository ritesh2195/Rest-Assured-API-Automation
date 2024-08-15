package test;

import utils.FileReader;
import api.IssueAPI;
import pojo.CommentPayload;
import utils.ExcelUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class AddCommentTest {

    @Steps
    IssueAPI endPoint;

    static String commentId;
    static ExcelUtil excelReader;
    static String id;
    CommentPayload payload = new CommentPayload();

    @Order(1)
    @Test
    public void addComment() throws IOException {

        excelReader = new ExcelUtil(FileReader.getInstance().getExcelFilePath());

        id = excelReader.getCellData("AddComment","Id",2);

        String comment = excelReader.getCellData("AddComment","Comment",2);

        String responseBody = endPoint.addComment(payload.payLoad(),"10220").then().assertThat()

                .statusCode(201).extract().body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        commentId = jsonPath.getString("id");

        String commentText = jsonPath.getString("body");

        Assert.assertEquals(commentText,comment);

        String getRequestBody = endPoint.getIssueDetails("10220").then().assertThat().statusCode(200).extract()

                .body().asString();

        JsonPath jsonPath1 = new JsonPath(getRequestBody);

        int commentCount = jsonPath1.getInt("fields.comment.comments.size()");

        Assert.assertEquals(jsonPath1.getString("fields.comment.comments["+(commentCount-1)+"].body"),comment);
    }

    @Order(2)
    @Test
    public void updateComment(){

        String updatedComment = excelReader.getCellData("AddComment","UpdatedComment",2);

        String body = endPoint.updateComment(id,commentId,payload.updateCommentPayload()).then()

                .assertThat().statusCode(200).extract().body().asString();

        endPoint.getIssueDetails(id).then().assertThat().statusCode(200).assertThat()

                .body("fields.comment.comments[0].body",equalTo(updatedComment));

    }
}
