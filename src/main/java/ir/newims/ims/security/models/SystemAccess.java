package ir.newims.ims.security.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_SystemAccess",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "method"),
      @UniqueConstraint(columnNames = "requestUri")
    })
public class SystemAccess extends BaseEntity {

  @NotBlank
  @Size(max = 10)
  private String method;

  @NotBlank
  @Size(max = 1000)
  private String requestUri;

  @NotBlank
  @Size(max = 1000)
  private String name;

  public SystemAccess() {
  }

  public SystemAccess(String method, String requestUri, String name) {
    this.method = method;
    this.requestUri = requestUri;
    this.name = name;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getRequestUri() {
    return requestUri;
  }

  public void setRequestUri(String requestUri) {
    this.requestUri = requestUri;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
