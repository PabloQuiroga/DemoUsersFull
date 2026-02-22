package org.siar.infrastructure.rest;

import io.quarkus.security.Authenticated;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.siar.application.usecase.GetUserUseCase;
import org.siar.application.usecase.LoginUserUseCase;
import org.siar.application.usecase.RegisterUserUseCase;
import org.siar.domain.model.User;
import org.siar.domain.service.TokenService;
import org.siar.infrastructure.rest.dto.LoginRequest;
import org.siar.infrastructure.rest.dto.LoginResponse;
import org.siar.infrastructure.rest.dto.RegisterUserRequest;
import org.siar.infrastructure.rest.dto.UserResponse;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    RegisterUserUseCase registerUserUseCase;

    @Inject
    LoginUserUseCase loginUserUseCase;

    @Inject
    GetUserUseCase getUserUseCase;

    @Inject
    TokenService tokenService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterUserRequest request) {
        try {
            User user = registerUserUseCase.execute(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
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

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request, @Context HttpServerRequest httpRequest) {
        try {
            String ipAddress = httpRequest.remoteAddress().host();
            User user = loginUserUseCase.execute(request.getUsername(), request.getPassword(), ipAddress);

            String token = tokenService.generateToken(user);

            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .status(user.getStatus())
                    .createdAt(user.getCreatedAt())
                    .build();

            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .message("Login successful")
                    .user(userResponse)
                    .build();

            return Response.ok(response).build();

        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/me")
    @Authenticated
    public Response me() {
        String username = jwt.getName();
        return getUserUseCase.execute(username)
                .map(user -> {
                    UserResponse response = UserResponse.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .status(user.getStatus())
                            .createdAt(user.getCreatedAt())
                            .build();
                    return Response.ok(response).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
