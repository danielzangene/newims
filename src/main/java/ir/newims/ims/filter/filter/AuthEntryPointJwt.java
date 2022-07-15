package ir.newims.ims.filter.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.exception.DataResponse;
import ir.newims.ims.exception.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    Response responseObject = new Response(ResponseConstant.SC_UNAUTHORIZED, ResponseConstantMessage.SC_UNAUTHORIZED);
    new ObjectMapper().writeValue(response.getOutputStream(), ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
            ResponseConstantMessage.SC_OK,
            responseObject)));
  }
  // TODO: 7/15/2022 fix this
//
//  {
//    "headers": {},
//    "body": {
//    "code": 200,
//            "message": "عملیات با موفقیت انجام شد.",
//            "resultData": {
//      "code": 401,
//              "message": "مدت زمان اعتبار توکن شما به پایان رسیده، لطفا مجدد وارد شوید."
//    }
//  },
//    "statusCode": "OK",
//          "statusCodeValue": 200
//  }
}
