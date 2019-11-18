package com.todolist.Object;

public abstract class Type {
    private String typeName;
    public Type(String typeName)
    {
        this.typeName=typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
