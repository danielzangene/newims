package ir.newims.ims.business.tmp;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.exception.DataResponse;
import ir.newims.ims.filter.dto.response.LoginResponse;
import ir.newims.ims.filter.utils.JwtUtils;
import ir.newims.ims.filter.dto.request.LoginRequest;
import ir.newims.ims.filter.dto.request.SignupRequest;
import ir.newims.ims.filter.repository.UserRepository;
import ir.newims.ims.filter.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class CController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/a")
    public ResponseEntity<?> aaaa(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginRequest.getUsername());
    }

    @PostMapping("/test")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK, ResponseConstantMessage.SC_OK,
                new LoginResponse(jwt)));
    }

    @GetMapping("/signup")
    public ResponseEntity<?> registerUser() {

        return ResponseEntity.ok("test");
    }
}
