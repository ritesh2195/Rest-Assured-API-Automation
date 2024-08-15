package test;

import api.IssueAPI;
import builders.JiraIssueBuilder;
import org.junit.BeforeClass;
import pojo.*;
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
    IssueAPI issueAPI;

    static String id;

    @Order(1)
    @Test
    public void postRequest(){

        Payload payload = new JiraIssueBuilder()
                .setProjectKey("RP")
                .setSummary("Feature is not working as expected")
                .setDescription("Creating an issue using project keys and issue type names using the REST API")
                .setIssueTypeName("Bug")
                .build();

        String responseBody = issueAPI.createIssue(payload).then().assertThat()

                .statusCode(201).extract().body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        String key = jsonPath.getString("key");

        id = jsonPath.getString("id");

        String getResponseBody = issueAPI.getIssueDetails(key).then().assertThat().

                statusCode(200).extract().body().asString();

        JsonPath jsonPath1 = new JsonPath(getResponseBody);

        Assert.assertEquals(jsonPath1.getString("key"),key);

        Assert.assertEquals(jsonPath1.getString("id"),id);

    }

    @AfterClass
    public static void deleteIssue(){

        issueAPI.deleteIssue(id).then().assertThat().statusCode(204);
    }
}
