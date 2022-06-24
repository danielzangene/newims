package ir.newims.ims.security.filter;

import ir.newims.ims.models.SystemAccess;
import ir.newims.ims.security.SecurityCodes;
import ir.newims.ims.security.repository.SystemAccessRepository;
import ir.newims.ims.security.repository.UserRepository;
import ir.newims.ims.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccessFilter extends OncePerRequestFilter {

    @Autowired
    SystemAccessRepository systemAccessRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info(AccessFilter.class.toString());
        if (isAnonymousUser()) {
            filterChain.doFilter(request, response);
            return;
        }
        String method = request.getMethod();
        String requestURI = request.getRequestURI().replaceAll("/$", "");

        if (hasPermission(method, requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            filterChain.doFilter(request, response);
        }
    }

    private Boolean isAnonymousUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return SecurityCodes.ANONYMOUS_USER.equals(principal);
    }

    private Boolean hasPermission(String method, String uri) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityCodes.ANONYMOUS_USER;
        if (!isAnonymousUser()) username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        List<SystemAccess> systemAccesses = systemAccessRepository.userHasAccess(username, method, uri);
        return !systemAccesses.isEmpty();
    }
}
