package com.jira.tests;

import com.jira.constants.APIHttpStatus;
import com.jira.utils.APIUtil;
import com.jira.utils.FileReaderUtil;
import com.jira.api.IssueAPI;
import com.jira.utils.ExcelUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class AttachmentTest {

    @Steps
    IssueAPI issueAPI;

    @Test
    public void addAttachment() throws IOException {

        String filePath = "src/test/resources/test-data/file.docx";

        String body = issueAPI
                .addAttachment("10220",filePath)
                .then()
                .spec(APIUtil.getResponseBuilder(APIHttpStatus.OK_200.getCode()))
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getString("filename[0]"),"file.docx");

        assertThat(jsonPath.getString("filename[0]"),equalTo("file.docx"));

    }
}
