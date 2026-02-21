package org.siar.infrastructure.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.siar.application.usecase.RegisterUserUseCase;
import org.siar.domain.model.User;
import org.siar.infrastructure.rest.dto.RegisterUserRequest;
import org.siar.infrastructure.rest.dto.UserResponse;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    RegisterUserUseCase registerUserUseCase;

    @POST
    @Path("/register")
    public Response register(RegisterUserRequest request) {
        try {
            User user = registerUserUseCase.execute(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword() // En un caso real, aquí deberías hashear la contraseña antes o dentro del caso de uso
            );

            UserResponse response = UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .status(user.getStatus())
                    .createdAt(user.getCreatedAt())
                    .build();

            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
