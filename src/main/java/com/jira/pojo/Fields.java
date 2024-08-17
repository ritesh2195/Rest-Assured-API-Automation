
package com.jira.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fields {

    @JsonProperty("project")
    @Getter @Setter
    private Project project;
    @JsonProperty("summary")
    @Getter @Setter
    private String summary;
    @JsonProperty("description")
    @Getter @Setter
    private String description;
    @JsonProperty("issuetype")
    @Getter @Setter
    private IssueType issuetype;
    @JsonProperty("parent")
    @Getter @Setter
    private Parent parent;

}
