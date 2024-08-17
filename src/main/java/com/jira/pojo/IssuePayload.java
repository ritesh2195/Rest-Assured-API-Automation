package com.jira.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IssuePayload {

    public ObjectNode payLoad(){

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode issue = mapper.createObjectNode();

        ObjectNode fields = mapper.createObjectNode();

        fields.put("summary","Feature is not working as expeted");

        fields.put("description","Creating of an issue using proj");

        ObjectNode project = mapper.createObjectNode();

        project.put("key","JAT");

        fields.set("project",project);

        ObjectNode issueType = mapper.createObjectNode();

        issueType.put("name","Bug");

        fields.set("issuetype",issueType);

        issue.set("fields",fields);

        return issue;
    }
}
