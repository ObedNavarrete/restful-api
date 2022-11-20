package project.base.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ResponseDTO {
    private String status;
    private String message;
    private String comment;
    private Object data;
}
