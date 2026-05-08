package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

public class UserSteps {
    public static User generateUser()
    {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "12345678";
        return new User(firstName, lastName, email, password);
    }
    public static String getToken(String email, String password)
    {
        User user = new User(email, password);
        Response response = UserApi.login(user);
        User userResponse = response.body().as(User.class);
        return userResponse.getAccessToken();
    }
}
