package com.example.test.Functionality;

public class Functionality {
    private String functionalityName;
    private Class<?> targetActivity;

    public Functionality(String functionalityName, Class<?> targetActivity) {
        this.functionalityName = functionalityName;
        this.targetActivity = targetActivity;
    }

    public String getFunctionalityName() {
        return functionalityName;
    }

    public Class<?> getTargetActivity() {
        return targetActivity;
    }
}