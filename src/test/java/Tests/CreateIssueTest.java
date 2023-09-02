package Tests;

import EndPoints.EndPoint;
import Pojo.IssuePayload;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class CreateIssueTest {

    @Steps
    static
    EndPoint endPoint;

    static String id;

    @Order(1)
    @Test
    public void postRequest(){

        IssuePayload issuePayload = new IssuePayload();

        String responseBody = endPoint.createIssue(issuePayload.payLoad()).then().assertThat()

                .statusCode(201).extract().body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        String key = jsonPath.getString("key");

        id = jsonPath.getString("id");

        String getResponseBody = endPoint.getIssueDetails(key).then().assertThat().

                statusCode(200).extract().body().asString();

        JsonPath jsonPath1 = new JsonPath(getResponseBody);

        Assert.assertEquals(jsonPath1.getString("key"),key);

        Assert.assertEquals(jsonPath1.getString("id"),id);

    }

    @AfterClass
    public static void deleteIssue(){

        endPoint.deleteIssue(id).then().assertThat().statusCode(204);
   }
}
