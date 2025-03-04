package com.para.demo.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;
import com.para.demo.port.NotePort;
import com.para.demo.port.SubjectPort;

@Component
public class TempAdaptor implements NotePort, SubjectPort{

    // 어댑터는 이렇게 구성됨. 특정 port 구현
    // 여기서는 임시로 ...테스트 실패하니까... 나니까. ..
    // 이렇게 한번에 여러게 포트를 구현하는 것은 
    // 케바케 임. 

    // 도메인 설계 끝.

    @Override
    public void registSubject(Long noteId, Long subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registSubject'");
    }

    @Override
    public void removeSubject(Long noteId, Long subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSubject'");
    }

    @Override
    public List<Subject> subjectList(Long noteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subjectList'");
    }

    @Override
    public Optional<Subject> addSubject(Subject newSubject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSubject'");
    }

    @Override
    public Optional<Subject> modifySubject(Subject modifiedSubject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifySubject'");
    }

    @Override
    public void removeSubject(Long subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSubject'");
    }

    @Override
    public Optional<Subject> getSubject(Long subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSubject'");
    }

    @Override
    public List<Subject> findSubjectList(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSubjectList'");
    }

    @Override
    public Optional<Subject> changePara(Long subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePara'");
    }

    @Override
    public Optional<Note> addNote(Note note) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addNote'");
    }

    @Override
    public Optional<Note> getNote(Long noteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNote'");
    }

    @Override
    public Optional<Note> modifyNote(Note modifiedNote) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyNote'");
    }

    @Override
    public void removeNote(Long noteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeNote'");
    }

    @Override
    public List<Note> noteList(String title) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'noteList'");
    }

    @Override
    public List<Note> notes(Long subjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notes'");
    }
    
}
