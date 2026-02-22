package org.siar.infrastructure.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.siar.infrastructure.rest.dto.LoginRequest;
import org.siar.infrastructure.rest.dto.RegisterUserRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceIT {

    // Use static fields to share data between ordered tests
    private static String username = "testuser_" + System.currentTimeMillis();
    private static String email = username + "@example.com";
    private static String password = "password123";
    private static String token;

    @Test
    @Order(1)
    public void testRegisterUser_Success() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users/register")
                .then()
                .statusCode(201)
                .body("username", equalTo(username))
                .body("email", equalTo(email))
                .body("id", notNullValue());
    }

    @Test
    @Order(2)
    public void testRegisterUser_Conflict() {
        // Try to register the same user again
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400) // Expect Bad Request
                .body("message", equalTo("Username already exists"));
    }

    @Test
    @Order(3)
    public void testLoginUser_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        // Extract the token and store it for the next test
        token = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("user.username", equalTo(username))
                .extract().path("token");
    }
    
    @Test
    @Order(4)
    public void testLoginUser_InvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword("wrongpassword");

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400) // From IllegalArgumentExceptionMapper
                .body("message", equalTo("Invalid credentials"));
    }

    @Test
    @Order(5)
    public void testProtectedEndpoint_Success() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("username", equalTo(username));
    }

    @Test
    @Order(6)
    public void testProtectedEndpoint_Unauthorized() {
        given()
                .when()
                .get("/users/me")
                .then()
                .statusCode(401); // Quarkus default for no token
    }
    
    @Test
    public void testRegisterUser_ValidationFailure() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("a"); // Too short
        request.setEmail("invalid-email"); // Invalid format
        request.setPassword("short"); // Too short

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400)
                .body("errors", hasSize(3));
    }
}
