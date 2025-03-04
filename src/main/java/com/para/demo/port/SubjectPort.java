package com.para.demo.port;

import java.util.List;
import java.util.Optional;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;

public interface SubjectPort {

    public void registSubject(Long noteId, Long subjectId) ;

    public void removeSubject(Long noteId, Long subjectId) ;

    // 노트에 등록된 주제
    public List<Subject> subjectList(Long noteId) ;

    public Optional<Subject> addSubject(Subject newSubject);

    public Optional<Subject> modifySubject(Subject modifiedSubject);

    public void removeSubject(Long subjectId);

    public Optional<Subject> getSubject(Long subjectId);

    // 검색
    public List<Subject> findSubjectList(String title);

    public Optional<Subject> changePara(Long subjectId);

}
