package org.siar.infrastructure.rest.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.siar.infrastructure.rest.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Map<String, String>> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            Map<String, String> error = new HashMap<>();
            String propertyPath = violation.getPropertyPath().toString();
            // Extract the field name from the property path (e.g., "register.arg0.username" -> "username")
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            
            error.put("field", fieldName);
            error.put("message", violation.getMessage());
            errors.add(error);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .error("Validation Error")
                .message("Input validation failed")
                .errors(errors)
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
    }
}
