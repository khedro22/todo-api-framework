package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {

    @JsonProperty("_id")
    private String id;

    private String item;

    private String userID;

    private String createdAt;

    @JsonProperty("__v")
    private String v;

    @JsonProperty("isCompleted")
    private boolean isCompleted;

    public Todo() {}
    public Todo(String item, boolean isCompleted)
    {
        this.item = item;
        this.isCompleted = isCompleted;
    }

    public Todo(String item)
    {
        this.item = item;
    }


    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("__v")
    public String getV() {
        return v;
    }

    @JsonProperty("__v")
    public void setV(String v) {
        this.v = v;
    }
}
