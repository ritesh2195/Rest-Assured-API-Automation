package com.jira.builders;

import com.jira.pojo.Fields;
import com.jira.pojo.IssueType;
import com.jira.pojo.Payload;
import com.jira.pojo.Project;

public class JiraIssueBuilder {

    private Project project;
    private String summary;
    private String description;
    private IssueType issueType;

    public JiraIssueBuilder setProjectKey(String key) {
        Project project = new Project();
        project.setKey(key);
        this.project = project;
        return this;
    }

    public JiraIssueBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public JiraIssueBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public JiraIssueBuilder setIssueTypeName(String name) {
        IssueType issueType = new IssueType();
        issueType.setName(name);
        this.issueType = issueType;
        return this;
    }

    public Payload build() {
        Fields fields = new Fields();
        fields.setProject(this.project);
        fields.setSummary(this.summary);
        fields.setDescription(this.description);
        fields.setIssuetype(this.issueType);

        Payload payload = new Payload();
        payload.setFields(fields);

        return payload;
    }
}
