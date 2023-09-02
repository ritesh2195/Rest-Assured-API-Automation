package Tests;

import EndPoints.EndPoint;
import Utility.ExcelReader;
import Utils.FileReader;
import io.restassured.RestAssured;
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
    EndPoint endPoint;

    @Test
    public void addAttachment() throws IOException {

        ExcelReader excelReader = new ExcelReader(FileReader.getInstance().getExcelFilePath());

        String id = excelReader.getCellData("AddComment","Id",2);

        String body = endPoint.addAttachment(id).then().assertThat().statusCode(200).extract().body().asString();

        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getString("filename[0]"),"file.docx");

    }
}
