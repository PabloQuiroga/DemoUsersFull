package org.siar.infrastructure.rest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.siar.infrastructure.rest.dto.ErrorResponse;

import java.time.LocalDateTime;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
