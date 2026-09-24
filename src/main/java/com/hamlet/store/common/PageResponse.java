package com.hamlet.store.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int size;
    private Long totalElements;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;
}
