package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {
    @Test
    public void shouldBeAbleToRegister()
    {
        User user = new User("Hatem", "Hatamleh", "hatem12345@example.com", "12345678");
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName", equalTo("Hatem"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmail()
    {
        User user = new User("Hatem", "Hatamleh", "hatem123@example.com", "12345678");
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/register")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldAbleToLogin()
    {
        User user = new User("hatem123@example.com", "12345678");
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("access_token", not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfTheEmailIsChanged()
    {
        User user = new User("hatem@example.com", "12345678");
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password"));

    }
}
