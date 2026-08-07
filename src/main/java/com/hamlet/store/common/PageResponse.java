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
    private int number;
    private int size;
    private int totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
}
