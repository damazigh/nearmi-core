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
        validatePaginationParam(limit, "limit", 1);
        validatePaginationParam(offset, "offset", 0);
        return true;
    }

    private void validatePaginationParam(String p, String pname, int min) {
        if (p != null) {
            try {
                int i = Integer.parseInt(p);
                if (i < min) {
                    throw new MiException(GeneralResKey.NMI_G_0005, pname);
                }
            } catch (Exception e) {
                throw new MiException(GeneralResKey.NMI_G_0004, pname);
            }
        }
    }
}
