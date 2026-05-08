package com.qacart.todo.testcases;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5ZjY0OGRkYzRiMTQxMDAxNTBmYjM4MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc3ODIyODI0Mn0.y5QlCD1fd_UxxfH2aiXy9U0mT9IPjDzJy4i0-LRdah0";
    @Test
    public void shouldBeAbleToAddTodo()
    {
        Todo todo = new Todo("Learn Java", false);
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("item", equalTo("Learn Java"))
                .assertThat().body("isCompleted", equalTo(false));
    }


    @Test
    public void shouldNotBeAbleToAddTodo()
    {
        Todo todo = new Todo("Learn Appium");
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then().log().all()
                .assertThat().statusCode(400);
    }

    @Test
    public void shouldBeAbleToDeleteTodo()
    {
        String taskID = "69fcba6c801a360015817b92";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("/api/v1/tasks/" + taskID)
                .then().statusCode(200);
    }
}
