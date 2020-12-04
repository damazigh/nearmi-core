package org.nearmi.core.dto.technical;

import lombok.Data;

import java.util.Collection;

@Data
public class PaginatedSearchResult<T> {
    Collection<T> content;
    int totalCount;
}
