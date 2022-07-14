package ir.newims.ims.filter.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.exception.Response;
import ir.newims.ims.models.personnel.systemaccess.SystemAccess;
import ir.newims.ims.filter.FilterCodes;
import ir.newims.ims.filter.repository.SystemAccessRepository;
import ir.newims.ims.filter.repository.UserRepository;
import ir.newims.ims.filter.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            Optional<SystemAccess> systemAccess = systemAccessRepository.findByMethodAndRequestUri(method, requestURI);
            Response responseObject = new Response(ResponseConstant.SC_METHOD_NOT_ALLOWED, getAccessMessageHandler(systemAccess.get().getName()));
            new ObjectMapper().writeValue(response.getOutputStream(), responseObject);
        }
    }

    private Boolean isAnonymousUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return FilterCodes.ANONYMOUS_USER.equals(principal);
    }

    private Boolean hasPermission(String method, String uri) {
        Optional<SystemAccess> systemAccess = systemAccessRepository.findByMethodAndRequestUri(method, uri);
        if (!systemAccess.isPresent()) return Boolean.TRUE;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = FilterCodes.ANONYMOUS_USER;
        if (!isAnonymousUser()) username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        List<SystemAccess> systemAccesses = systemAccessRepository.userHasAccess(username, method, uri);
        return !systemAccesses.isEmpty();
    }

    private String getAccessMessageHandler(String useCaseName) {
        return String.format(ResponseConstantMessage.SC_METHOD_NOT_ALLOWED, useCaseName);
    }

}
