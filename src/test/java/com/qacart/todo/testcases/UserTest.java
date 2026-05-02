package com.qacart.todo.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {
    @Test
    public void shouldBeAbleToRegister()
    {
        String body = "{\n" +
                "  \"firstName\": \"Hatem\",\n" +
                "  \"lastName\": \"Hatamleh\",\n" +
                "  \"email\": \"hatem123@example.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName", equalTo("Hatem"));

    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmail()
    {
        String body = "{\n" +
                "  \"firstName\": \"Hatem\",\n" +
                "  \"lastName\": \"Hatamleh\",\n" +
                "  \"email\": \"hatem123@example.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldAbleToLogin()
    {
        String body = "{\n" +
                "  \"email\": \"hatem123@example.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("access_token", not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfTheEmailIsChanged()
    {
        String body = "{\n" +
                "  \"email\": \"hatem@example.com\",\n" +
                "  \"password\": \"12345678\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password"));

    }
}
