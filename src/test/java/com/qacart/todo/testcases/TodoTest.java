package com.qacart.todo.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5ZjY0OGRkYzRiMTQxMDAxNTBmYjM4MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc3ODE5NDY3Mn0.acusDrFJRcUECYGUyeEysGh63um6WlX3btsiv1fGyCE";
    @Test
    public void shouldBeAbleToAddTodo()
    {
        String body = "{\n" +
                "\"isCompleted\": false,\n" +
                "\"item\": \"Learn Appium\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("item", equalTo("Learn Appium"))
                .assertThat().body("isCompleted", equalTo(false));
    }


    @Test
    public void shouldNotBeAbleToAddTodo()
    {
        String body = "{\n" +
                "\"isCompleted\": false,\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(body)
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
