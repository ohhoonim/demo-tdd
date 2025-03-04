package com.para.demo.model;

public enum ParaEnum {
    PROJECT("project"),
    AREA("area"),
    RESOURCE("resource"),
    ARCHIVE("archive");

    private final String value;

    ParaEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
