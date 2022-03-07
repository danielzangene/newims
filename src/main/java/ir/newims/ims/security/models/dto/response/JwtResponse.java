package ir.newims.ims.security.models.dto.response;

public class JwtResponse {
  private String token;
  private Long id;
  private int status;

  public JwtResponse(String accessToken, Long id, int status) {
    this.token = accessToken;
    this.id = id;
    this.status = status;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }
   
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
