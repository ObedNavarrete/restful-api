package project.base.security.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class PageResponse {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean last;
    private Object content;
}
