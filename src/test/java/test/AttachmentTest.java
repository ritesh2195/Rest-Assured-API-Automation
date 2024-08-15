package test;

import Utils.FileReader;
import api.IssueAPI;
import utils.ExcelUtil;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;

@RunWith(SerenityRunner.class)
public class AttachmentTest {

    @Steps
    IssueAPI issueAPI;

    @Test
    public void addAttachment() throws IOException {

        ExcelUtil excelReader = new ExcelUtil(FileReader.getInstance().getExcelFilePath());

        String id = excelReader.getCellData("AddComment","Id",2);

        String body = issueAPI.addAttachment("10220").then().assertThat().statusCode(200).extract().body().asString();

        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getString("filename[0]"),"file.docx");

    }
}
