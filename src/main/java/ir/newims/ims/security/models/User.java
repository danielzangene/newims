package ir.newims.ims.security.models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_User",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username")
    })
public class User extends BaseEntity {

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(  name = "mm_UserAccess",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "systemAccess_id"))
  private List<SystemAccess> accessList;

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<SystemAccess> getAccessList() {
    return accessList;
  }

  public void setAccessList(List<SystemAccess> accessList) {
    this.accessList = accessList;
  }
}
