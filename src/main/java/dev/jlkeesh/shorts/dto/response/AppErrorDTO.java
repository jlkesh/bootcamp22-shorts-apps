package dev.jlkeesh.shorts.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppErrorDTO {
    private String errorPath;
    private String errorMessage;
    private Integer errorCode;
    private Object errorBody;
    private Long timestamp;

    public AppErrorDTO(String errorPath, String errorMessage, Integer errorCode) {
        this(errorPath, errorMessage, null, errorCode);
    }

    public AppErrorDTO(String errorPath, String errorMessage, Object errorBody, Integer errorCode) {
        this.errorPath = errorPath;
        this.errorMessage = errorMessage;
        this.errorBody = errorBody;
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
}
