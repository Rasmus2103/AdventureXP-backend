package com.example.adventurexp.security.dto;

import com.example.adventurexp.security.entity.UserWithRoles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserWithRolesResponse {
    String userName;
    List<String> roleNames;
    String email;

    public UserWithRolesResponse(UserWithRoles userWithRoles){
        this.userName = userWithRoles.getUsername();
        this.roleNames = userWithRoles.getRoles().stream().map(role -> role.toString()).collect(Collectors.toList());
        this.email = userWithRoles.getEmail();
    }

}
