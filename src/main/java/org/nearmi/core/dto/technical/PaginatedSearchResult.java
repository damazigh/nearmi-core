package org.nearmi.core.dto.technical;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Collections;

@Getter
public class PaginatedSearchResult<T> {
    private Collection<T> content;
    private long totalCount;

    private PaginatedSearchResult(Page<T> page) {
        if (page != null) {
            this.content = page.getContent();
            this.totalCount = page.getTotalElements();
        } else {
            content = Collections.emptyList();
            totalCount = 0;
        }
    }

    public static <T> PaginatedSearchResult<T> of(Page<T> page) {
        return new PaginatedSearchResult<>(page);
    }

    public int whichStatus() {
        return content.size() == totalCount ?
                HttpStatus.OK.value() : HttpStatus.PARTIAL_CONTENT.value();
    }
}
