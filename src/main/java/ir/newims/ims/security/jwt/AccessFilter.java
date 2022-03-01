package ir.newims.ims.security.jwt;

import ir.newims.ims.security.models.SystemAccess;
import ir.newims.ims.security.models.User;
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
import java.util.Objects;
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
        try {
            String requestURI = request.getRequestURI().replaceAll("/$", "");
            String method = request.getMethod();
            System.out.println(AccessFilter.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                if ("anonymousUser".equals(principal)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
            Optional<SystemAccess> systemAccessOptional = systemAccessRepository.findByMethodAndRequestUri(method, requestURI);
            if (systemAccessOptional.isPresent()) {
                List<SystemAccess> systemAccesses = systemAccessRepository.userHasAccess(userOptional.get(), systemAccessOptional.get());
                if (systemAccesses.isEmpty()) {
                    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            logger.error("Cannot find SystemAccess: {}", e);
        }

    }
}
