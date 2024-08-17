package com.jira.tests;

import com.jira.api.IssueAPI;
import com.jira.constants.APIHttpStatus;
import com.jira.utils.ExcelUtil;
import com.jira.utils.FileReaderUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

@RunWith(SerenityRunner.class)
public class GetIssueTest {

    @Steps
    IssueAPI endPoint;

    @Test
    public void getRequest() throws IOException {

        ExcelUtil excelReader = new ExcelUtil(FileReaderUtil.getInstance().getExcelFilePath());

        String key = excelReader.getCellData("AddComment","Key",2);

        String responseBody = endPoint.getIssueDetails(key).then().assertThat().
                statusCode(APIHttpStatus.OK_200.getCode()).extract().body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        String responseKey = jsonPath.getString("key");

        assertThat(responseKey,equalTo(key));

        String issueType = jsonPath.getString("fields.issuetype.name");

        assertThat(issueType,equalTo("Bug"));

    }
}
