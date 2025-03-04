package com.para.demo.service;

import java.util.List;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;

public interface SubjectUsecase {
    public Subject addSubject(Subject newSubject);

    public Subject modifySubject(Subject modifiedSubject);

    public void removeSubject(Long subjectId);

    public Subject getSubject(Long subjectId);

    public List<Subject> subjectList(String title);

    public List<Note> noteList(Long subjectId);

    public Subject changePara(Subject modifiedPara);
}
