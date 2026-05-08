package com.qacart.todo.testcases;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5ZjY0OGRkYzRiMTQxMDAxNTBmYjM4MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc3ODIyODI0Mn0.y5QlCD1fd_UxxfH2aiXy9U0mT9IPjDzJy4i0-LRdah0";
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
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("item"), equalTo("Learn Java"));
        assertThat(response.path("isCompleted"), equalTo(false));
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
