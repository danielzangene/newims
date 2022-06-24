package ir.newims.ims.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Response {

    @JsonProperty("code")
    int code;

    @JsonProperty("message")
    String message;

    public static Response SUCCESS_RESPONSE =
            new Response(ResponseConstant.SC_OK, ResponseConstantMessage.SC_OK);

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
