package org.siar.infrastructure.rest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.siar.infrastructure.rest.dto.ErrorResponse;

import java.time.LocalDateTime;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(IllegalStateException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.FORBIDDEN.getStatusCode()) // 403 Forbidden for blocked user
                .error("Forbidden")
                .message(exception.getMessage())
                .build();

        return Response.status(Response.Status.FORBIDDEN).entity(errorResponse).build();
    }
}
