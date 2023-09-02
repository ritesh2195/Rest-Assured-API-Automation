package EndPoints;

import Utils.FileReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.io.File;

public class EndPoint {

    private final RequestSpecification specification;

    public EndPoint(){

        String userName = FileReader.getInstance().getUserName();

        String password = FileReader.getInstance().getPassword();

        RestAssured.baseURI = FileReader.getInstance().getEndPoint();

        specification = SerenityRest.given().auth().preemptive().basic(userName,password).

                contentType(ContentType.JSON);
    }

    @Step
    public Response getIssueDetails(String key){

        return specification.when().get("/"+key);

    }

    @Step
    public Response createIssue(ObjectNode node){

        return specification.body(node).when().post();
    }

    @Step
    public Response deleteIssue(String id){

        return specification.when().delete("/"+id);
    }

    @Step
    public Response addComment(ObjectNode node, String id){

        return specification.body(node).when().post("/"+id+"/comment");

    }

    @Step
    public Response addAttachment(String id){

        File file = new File(FileReader.getInstance().getAttachmentFilePath());

        return specification.contentType(ContentType.MULTIPART).header("X-Atlassian-Token","nocheck").

                multiPart("file",file).when().post("/"+id+"/attachments");
    }

    @Step
    public Response updateComment(String issueId, String commentId, ObjectNode node){

        return specification.body(node).when().put("/"+issueId+"/comment/"+commentId);
    }
}
