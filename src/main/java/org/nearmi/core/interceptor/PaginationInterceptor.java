package org.nearmi.core.interceptor;

import org.nearmi.core.exception.MiException;
import org.nearmi.core.resource.GeneralResKey;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PaginationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        validatePaginationParam(limit, "limit");
        validatePaginationParam(offset, "offset");
        return true;
    }

    private void validatePaginationParam(String p, String pname) {
        if (p != null) {
            try {
                Integer.parseInt(p);
            } catch (Exception e) {
                throw new MiException(GeneralResKey.NMI_G_0004, pname);
            }
        }
    }
}
