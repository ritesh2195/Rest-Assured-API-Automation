package com.jira.tests;

import com.jira.api.IssueAPI;
import com.jira.builders.JiraIssueBuilder;
import com.jira.constants.APIHttpStatus;
import com.jira.pojo.Payload;
import com.jira.utils.APIUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import net.thucydides.core.annotations.Steps;
import org.junit.AfterClass;
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

        String responseBody = issueAPI
                .createIssue(payload)
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.CREATED_201.getCode()))
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        String key = jsonPath.getString("key");

        id = jsonPath.getString("id");

        String getResponseBody = issueAPI
                .getIssueDetails(key)
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .extract()
                .body()
                .asString();

        JsonPath jsonPath1 = new JsonPath(getResponseBody);

        assertThat(jsonPath1.getString("key"),equalTo(key));

        assertThat(jsonPath1.getString("id"),equalTo(id));

    }

    @AfterClass
    public static void deleteIssue(){

        issueAPI
                .deleteIssue(id)
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.NO_CONTENT_204.getCode()));
    }
}
