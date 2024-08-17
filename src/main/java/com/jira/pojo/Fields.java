
package com.jira.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fields {

    @JsonProperty("project")
    private Project project;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("issuetype")
    private IssueType issuetype;
    @JsonProperty("parent")
    private Parent parent;

    @JsonProperty("project")
    public Project getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(Project project) {
        this.project = project;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("issuetype")
    public IssueType getIssuetype() {
        return issuetype;
    }

    @JsonProperty("issuetype")
    public void setIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
    }

    @JsonProperty("parent")
    public  Parent getParent() {return parent; }

    @JsonProperty("parent")
    public void setParent(Parent parent) { this.parent = parent; }

}
