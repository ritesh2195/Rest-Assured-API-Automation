package com.jira.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {

    @JsonProperty("body")
    private String body;

    @JsonProperty("body")
    public String getBody(){return body;}

    @JsonProperty("body")
    public void setBody(String body){this.body = body;}
}
