package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {
    @Test
    public void shouldBeAbleToRegister()
    {
        User user = new User("Hatem", "Hatamleh", "hate_m12345@example.com", "12345678");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/register")
                .then()
                .log().all().extract().response();

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("firstName"), equalTo("Hatem"));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmail()
    {
        User user = new User("Hatem", "Hatamleh", "hatem123@example.com", "12345678");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/register")
                .then()
                .log().all().extract().response();
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldAbleToLogin()
    {
        User user = new User("hatem123@example.com", "12345678");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/login")
                .then()
                .log().all().extract().response();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("access_token"), not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfTheEmailIsChanged()
    {
        User user = new User("hatem@example.com", "12345678");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/login")
                .then()
                .log().all().extract().response();
        assertThat(response.statusCode(), equalTo(401));
        assertThat(response.path("message"), equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}
