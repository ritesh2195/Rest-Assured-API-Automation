package com.jira.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jira.constants.JiraEndPoint;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import com.jira.utils.FileReaderUtil;

public class IssueAPI {

    private final RequestSpecification specification;

    public IssueAPI(){

        String userName = FileReaderUtil.getInstance().getUserName();

        String password = FileReaderUtil.getInstance().getPassword();

        RestAssured.baseURI = FileReaderUtil.getInstance().getBaseURI()+ JiraEndPoint.ISSUE.getEndPoint();

        specification = SerenityRest.given().auth().preemptive().basic(userName,password).

                contentType(ContentType.JSON);
    }

    @Step
    public Response getIssueDetails(String key){

        return specification.when().get("/"+key);

    }

    @Step
    public Response createIssue(Object requestBody){

        return specification.body(requestBody).when().post();
    }

    @Step
    public Response deleteIssue(String id){

        return specification.when().delete("/"+id);
    }

    @Step
    public Response addComment(Object requestBody, String id){

        return specification.body(requestBody).when().post("/"+id+"/comment");

    }

    @Step
    public Response addAttachment(String id,String file){

        return specification.contentType(ContentType.MULTIPART).header("X-Atlassian-Token","nocheck").

                multiPart("file",file).when().post("/"+id+"/attachments");
    }

    @Step
    public Response updateComment(String issueId, String commentId, Object payload){

        return specification.body(payload).when().put("/"+issueId+"/comment/"+commentId);
    }
}
