package api;

import utils.FileReader;
import pojo.Payload;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.io.File;

public class IssueAPI {

    private final RequestSpecification specification;

    public IssueAPI(){

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
    public Response createIssue(Payload node){

        return specification.body(node).when().post();
    }

    @Step
    public Response deleteIssue(String id){

        return specification.when().delete("/"+id);
    }

    @Step
    public Response addComment(ObjectNode payload, String id){

        return specification.body(payload).when().post("/"+id+"/comment");

    }

    @Step
    public Response addAttachment(String id){

        File file = new File(FileReader.getInstance().getAttachmentFilePath());

        return specification.contentType(ContentType.MULTIPART).header("X-Atlassian-Token","nocheck").

                multiPart("file",file).when().post("/"+id+"/attachments");
    }

    @Step
    public Response updateComment(String issueId, String commentId, ObjectNode payload){

        return specification.body(payload).when().put("/"+issueId+"/comment/"+commentId);
    }
}
