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

    //To exclude the default value of isCompleted (false)
    //@JsonInclude(JsonInclude.Include.NON_DEFAULT) ----> not efficient
    @JsonProperty("isCompleted")
    private Boolean isCompleted;

    public Todo() {}
    public Todo(String item, Boolean isCompleted)
    {
        this.item = item;
        this.isCompleted = isCompleted;
    }

    public Todo(String item)
    {
        this.item = item;
    }

    @JsonProperty("isCompleted")
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    @JsonProperty("isCompleted")
    public void setIsCompleted(Boolean completed) {
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
