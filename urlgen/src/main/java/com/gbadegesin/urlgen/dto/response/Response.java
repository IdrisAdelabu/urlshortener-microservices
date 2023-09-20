package com.gbadegesin.urlgen.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String responseMessage;
    private String responseCode;
    private Object data;
}
