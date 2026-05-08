package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
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
        User user = new User("Hatem", "Hatamleh", "hatem123_45@example.com", "12345678");
        Response response = UserApi.register(user);
        // Deserialization
        User userResponse = response.body().as(User.class);
        // Static Assertions
        assertThat(response.statusCode(), equalTo(201));
        assertThat(userResponse.getFirstName(), equalTo(user.getFirstName()));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmail()
    {
        User user = new User("Hatem", "Hatamleh", "hatem123@example.com", "12345678");
        Response response = UserApi.register(user);
        Error error = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(error.getMessage(), equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldAbleToLogin()
    {
        User user = new User("hatem123@example.com", "12345678");
        Response response = UserApi.login(user);
        User userResponse = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(userResponse.getFirstName(), equalTo("Hatem"));
        assertThat(userResponse.getAccessToken(), not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfTheEmailIsChanged()
    {
        User user = new User("hatem@example.com", "12345678");
        Response response = UserApi.login(user);
        Error error = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(error.getMessage(), equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}
