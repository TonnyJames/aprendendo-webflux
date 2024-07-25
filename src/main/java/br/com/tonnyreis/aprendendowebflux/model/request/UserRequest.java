package br.com.tonnyreis.aprendendowebflux.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @Size(min = 3, max = 50, message = "must have a minimum size of 3 and maximum of 50 characters")
        @NotBlank(message = "must not be null or empty")
        String name,

        @Email(message = "invalid email")
        @NotBlank(message = "must not be null or empty")
        String email,

        @Size(min = 3, max = 20, message = "must have a minimum size of 3 and maximum of 20 characters")
        @NotBlank(message = "must not be null or empty")
        String password
) { }
