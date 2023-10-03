package security.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configurable
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR_TYPE")
public class UserWithRoles implements UserDetails {

  /*
  This is not very elegant since the password encoder is hardcoded, and eventually could end as being different from the one used in the project
  It's done this way, to make it easier to use this semester, since this class hashes and salts passwords automatically
  Also it's done like this since YOU CANNOT inject beans into entities
   */
  @Transient
  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Id
  @Column(nullable = false, length = 50, unique = true)
  String username;

  @Column(nullable = false, length = 50, unique = true)
  String email;

  //60 = length of a bcrypt encoded password
  @Column(nullable = false, length = 60)
  String password;

  private boolean enabled = true;

  @CreationTimestamp
  private LocalDate created;

  @UpdateTimestamp
  private LocalDate edited;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "ENUM('USER','ADMIN')")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "security_role")
  List<Role> roles = new ArrayList<>();

  public UserWithRoles() {
  }


  public UserWithRoles(String user, String password, String email) {
    this.username = user;
    setPassword(password);
    this.email = email;
  }

  public void setPassword(String pw) {
    this.password = passwordEncoder.encode(pw);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
  }

  public void addRole(Role roleToAdd) {
    if (!roles.contains(roleToAdd)) {
      roles.add(roleToAdd);
    }
  }

  public void removeRole(Role roleToRemove) {
    if (roles.contains(roleToRemove)) {
      roles.remove(roleToRemove);
    }
  }

  //You can, but are NOT expected to use the fields below
  @Override
  public boolean isAccountNonExpired() {
    return enabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return enabled;
  }
}