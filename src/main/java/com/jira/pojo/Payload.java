
package com.jira.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    @JsonProperty("fields")
    @Getter @Setter
    private Fields fields;

}
