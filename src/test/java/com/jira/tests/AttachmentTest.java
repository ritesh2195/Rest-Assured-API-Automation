package com.jira.tests;

import com.jira.builders.JiraIssueBuilder;
import com.jira.constants.APIHttpStatus;
import com.jira.pojo.Comment;
import com.jira.pojo.Payload;
import com.jira.utils.APIUtil;
import com.jira.utils.FileReaderUtil;
import com.jira.api.IssueAPI;
import com.jira.utils.ExcelUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class AttachmentTest {

    @Steps
    IssueAPI issueAPI;
    static String id;

    @Before
    public void setUp(){
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

    @Test
    public void addAttachment() throws IOException {

        String filePath = "src/test/resources/test-data/file.docx";

        String body = issueAPI
                .addAttachment(id,filePath)
                .then()
                .log()
                .all()
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getString("filename[0]"),"file.docx");

        assertThat(jsonPath.getString("filename[0]"),equalTo("file.docx"));

    }
}
