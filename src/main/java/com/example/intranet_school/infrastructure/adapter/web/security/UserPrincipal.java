package com.example.intranet_school.infrastructure.adapter.web.security;

import java.security.Principal;

public class UserPrincipal implements Principal {
    private final Long id;
    private final String email;

    public UserPrincipal(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() { return id; }

    @Override
    public String getName() { return email; }
}
