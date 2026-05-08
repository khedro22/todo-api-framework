package com.qacart.todo.testcases;

import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5ZjY0OGRkYzRiMTQxMDAxNTBmYjM4MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc3ODI0MzQwNX0.eQptiB_eaMS6pFrjy9qTjw9RyGlFMZdH1q1U_lAxDrE";
    @Test
    public void shouldBeAbleToAddTodo()
    {
        Todo todo = new Todo("Learn Java", false);
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then().log().all().extract().response();

        Todo todoResponse = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(todoResponse.getItem(), equalTo(todo.getItem()));
        assertThat(todoResponse.getIsCompleted(), equalTo(todo.getIsCompleted()));
    }


    @Test
    public void shouldNotBeAbleToAddTodo()
    {
        Todo todo = new Todo("Learn Appium");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then().log().all().extract().response();

        Error error = response.body().as(Error.class);
        assertThat(error.getMessage(),
                equalTo("\"isCompleted\" is required"));
        assertThat(response.statusCode(), equalTo(400));
    }

    @Test
    public void shouldBeAbleToDeleteTodo()
    {
        String taskID = "69fdb19a2873ba00156e34f1";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("/api/v1/tasks/" + taskID)
                .then().log().all().extract().response();
        assertThat(response.statusCode(), equalTo(200));
    }
}
