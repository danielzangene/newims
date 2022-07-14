package ir.newims.ims.models.personnel.personnel;

import ir.newims.ims.models.BaseEntity;
import ir.newims.ims.models.personnel.systemaccess.GroupSystemAccess;

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
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(max = 100)
  @Email
  private String email;

  @NotBlank
  @Size(max = 1000)
  private String password;

  private List<GroupSystemAccess> groupAccess;

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "mm_userGroupAccess",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "group_id"))
  public List<GroupSystemAccess> getGroupAccess() {
    return groupAccess;
  }

  public void setGroupAccess(List<GroupSystemAccess> groupAccess) {
    this.groupAccess = groupAccess;
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

}
