package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessage;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    String token = UserSteps.getToken("hatem123@example.com", "12345678");

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
                equalTo(ErrorMessage.COMPLETED_REQUIRED));
        assertThat(response.statusCode(), equalTo(400));
    }

    @Test
    public void shouldBeAbleToGetTodo()
    {
        String taskID = "69fe01022873ba00156e38a6";
        Response response = TodoApi.getTodo(taskID, token);
        Todo todoResponse = response.body().as(Todo.class);
        assertThat(todoResponse.getItem(), equalTo("Learn Java"));
    }

    @Test
    public void shouldBeAbleToDeleteTodo()
    {
        String taskID = "69fe01022873ba00156e38a6";
        Response response = TodoApi.deleteTodo(taskID, token);
        assertThat(response.statusCode(), equalTo(200));
    }
}
