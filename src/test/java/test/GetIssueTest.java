package test;

import api.IssueAPI;
import utils.ExcelUtil;
import Utils.FileReader;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(SerenityRunner.class)
public class GetIssueTest {

    @Steps
    IssueAPI endPoint;

    @Test
    public void getRequest() throws IOException {

        ExcelUtil excelReader = new ExcelUtil(FileReader.getInstance().getExcelFilePath());

        String key = excelReader.getCellData("AddComment","Key",2);

        String responseBody = endPoint.getIssueDetails(key).then().assertThat().statusCode(200).extract().body()
                .asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        String responseKey = jsonPath.getString("key");

        Assert.assertEquals(responseKey,key);

        String issueType = jsonPath.getString("fields.issuetype.name");

        Assert.assertEquals(issueType,"Bug");

    }
}
