package ir.newims.ims.security.models.dto.response;

public class JwtResponse {
  private String token;
  private Long id;
  private String username;
  private String email;
  private int status;

  public JwtResponse(String accessToken, Long id, String username, String email, int status) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
