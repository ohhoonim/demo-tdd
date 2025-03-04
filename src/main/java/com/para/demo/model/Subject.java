package com.para.demo.model;

public record Subject(
    Long subjectId,
    String title,
    String content,
    ParaEnum para
) {}

