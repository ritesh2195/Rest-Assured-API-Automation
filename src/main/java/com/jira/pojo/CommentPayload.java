package com.jira.pojo;

import com.jira.utils.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class CommentPayload {

    ExcelUtil reader;

    public CommentPayload() {

        try {

            reader = new ExcelUtil("src/test/resources/TestData/IssueData.xlsx");

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public ObjectNode payLoad() {

        String comment = reader.getCellData("AddComment","Comment",2);

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();

        node.put("body",comment);

        return node;
    }

    public ObjectNode updateCommentPayload(){

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();

        node.put("body",reader.getCellData("AddComment","UpdatedComment",2));

        return node;
    }
}
