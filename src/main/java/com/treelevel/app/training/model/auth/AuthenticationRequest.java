package com.treelevel.app.training.model.auth;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}
