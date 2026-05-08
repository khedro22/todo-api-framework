package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5ZjY0OGRkYzRiMTQxMDAxNTBmYjM4MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc3ODI1MDgzNH0.BTlW3IZ5UR2Xk4kvQF8L6RrDtLe7CSP5BWaOIkTckdU";

    @Test
    public void shouldBeAbleToAddTodo()
    {
        Todo todo = new Todo("Learn Java", false);
        Response response = TodoApi.addTodo(todo, token);
        Todo todoResponse = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(todoResponse.getItem(), equalTo(todo.getItem()));
        assertThat(todoResponse.getIsCompleted(), equalTo(todo.getIsCompleted()));
    }


    @Test
    public void shouldNotBeAbleToAddTodo()
    {
        Todo todo = new Todo("Learn Appium");
        Response response = TodoApi.addTodo(todo, token);

        Error error = response.body().as(Error.class);
        assertThat(error.getMessage(),
                equalTo("\"isCompleted\" is required"));
        assertThat(response.statusCode(), equalTo(400));
    }

    @Test
    public void shouldBeAbleToGetTodo()
    {
        String taskID = "69fdf64a2873ba00156e3825";
        Response response = TodoApi.getTodo(taskID, token);
        Todo todoResponse = response.body().as(Todo.class);
        assertThat(todoResponse.getItem(), equalTo("Learn Java"));
    }

    @Test
    public void shouldBeAbleToDeleteTodo()
    {
        String taskID = "69fdf54b2873ba00156e3820";
        Response response = TodoApi.deleteTodo(taskID, token);
        assertThat(response.statusCode(), equalTo(200));
    }
}
