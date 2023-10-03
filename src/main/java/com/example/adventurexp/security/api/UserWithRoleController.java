package com.example.adventurexp.security.api;

import com.example.adventurexp.security.dto.UserWithRolesRequest;
import com.example.adventurexp.security.dto.UserWithRolesResponse;
import com.example.adventurexp.security.entity.Role;
import com.example.adventurexp.security.service.UserWithRolesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-with-role")
public class UserWithRoleController {

  //Take care with this. If no role is required for new users, add null as the value below
  static Role DEFAULT_ROLE_TO_ASSIGN = Role.USER;

  UserWithRolesService userWithRolesService;

  public UserWithRoleController(UserWithRolesService userWithRolesService) {
    this.userWithRolesService = userWithRolesService;
  }

  //Anonymous users can call this. Set DEFAULT_ROLE_TO_ASSIGN to null if no role should be added
  @PostMapping
  public UserWithRolesResponse addUserWithRoles(@RequestBody UserWithRolesRequest request) {
    return userWithRolesService.addUserWithRoles (request, DEFAULT_ROLE_TO_ASSIGN);
  }

  //Take care with this. This should NOT be something everyone can call
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/add-role/{username}/{role}")
  public UserWithRolesResponse addRole(@PathVariable String username, @PathVariable String role) {
    return userWithRolesService.addRole(username, Role.fromString(role));
  }
  //Take care with this. This should NOT be something everyone can call
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/remove-role/{username}/{role}")
  public UserWithRolesResponse removeRole(@PathVariable String username, @PathVariable String role) {
    return userWithRolesService.removeRole(username, Role.fromString(role));
  }
}
