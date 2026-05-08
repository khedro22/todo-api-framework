package com.qacart.todo.apis;

import com.qacart.todo.data.Route;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
    public static Response register(User user)
    {
        return given()
                .baseUri(Route.BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .when().post(Route.REGISTER_ROUTE)
                .then()
                .log().all().extract().response();
    }

    public static Response login(User user)
    {
        return given()
                .baseUri(Route.BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .when().post(Route.REGISTER_LOGIN)
                .then()
                .log().all().extract().response();
    }
}
