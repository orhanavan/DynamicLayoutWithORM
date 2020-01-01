package com.orhan.project.model;

public class Input {

    private String title;

    private InputType inputType;

    public Input(String title, InputType inputType) {
        this.title = title;
        this.inputType = inputType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputType getInputType() {
        return inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }
}
