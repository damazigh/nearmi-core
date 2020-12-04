package org.nearmi.core.util;

import org.nearmi.core.dto.technical.PaginatedSearchResult;

import javax.servlet.http.HttpServletResponse;

public class HttpUtils {
    /**
     * publish pagination header for a given http servlet response
     *
     * @param response an http response (before commit)
     * @param result   result of the search
     * @param <T>      type of the result search
     */
    public static <T> void addPaginationHeader(HttpServletResponse response, PaginatedSearchResult<T> result) {
        int resultCount = 0;
        int totalCount = 0;
        if (result != null && result.getContent() != null && !result.getContent().isEmpty()) {
            resultCount = result.getContent().size();
            totalCount = result.getTotalCount();
        }
        response.addHeader("X-RESULT-COUNT", String.valueOf(resultCount));
        response.addHeader("X-TOTAL-COUNT", String.valueOf(totalCount));
    }
}
