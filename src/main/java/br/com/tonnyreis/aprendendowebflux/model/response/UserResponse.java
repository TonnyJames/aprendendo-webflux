package br.com.tonnyreis.aprendendowebflux.model.response;

public record UserResponse(

        String id,
        String name,
        String email,
        String password
) { }
