package com.project.operations.domain;

public class Expression {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long newValue) {
        if (id != newValue) {
            id = newValue;
        }
    }
}
