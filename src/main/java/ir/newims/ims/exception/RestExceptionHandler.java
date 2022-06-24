package ir.newims.ims.exception;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultExceptionHandler(Exception e) {
        Response response = new Response(ResponseConstant.INTERNAL_ERROR, ResponseConstantMessage.INTERNAL_ERROR);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity businessExceptionHandler(BusinessException e) {
        Response response = new Response(e.getCode(), e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsExceptionHandler(BadCredentialsException e) {
        Response response = new Response(ResponseConstant.SC_BAD_REQUEST, ResponseConstantMessage.INVALID_LOGIN_DATA);
        return ResponseEntity.badRequest().body(response);
    }


}
