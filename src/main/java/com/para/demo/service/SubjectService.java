package com.para.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;
import com.para.demo.port.NotePort;
import com.para.demo.port.SubjectPort;

@Service
public class SubjectService implements SubjectUsecase {

    private final SubjectPort subjectPort;
    private final NotePort notePort;

    public SubjectService(SubjectPort subjectPort, NotePort notePort) {
        this.notePort = notePort;
        this.subjectPort = subjectPort;
    }

    @Override
    public Subject addSubject(Subject newSubject) {
        return subjectPort.addSubject(newSubject)
            .orElseThrow(() -> new RuntimeException("Failed to add subject"));
    }

    @Override
    public Subject modifySubject(Subject modifiedSubject) {
        return subjectPort.modifySubject(modifiedSubject)
            .orElseThrow(() -> new RuntimeException("Failed to modify subject"));
    }

    @Override
    public void removeSubject(Long subjectId) {
        subjectPort.removeSubject(subjectId);
    }

    @Override
    public Subject getSubject(Long subjectId) {
        return subjectPort.getSubject(subjectId)
            .orElseThrow(() -> new RuntimeException("Failed to get subject"));
    }

    @Override
    public List<Subject> subjectList(String title) {
        return subjectPort.findSubjectList(title);
    }

    @Override
    public List<Note> noteList(Long subjectId) {
        return notePort.notes(subjectId);
    }

    @Override
    public Subject changePara(Subject subject) {
        return subjectPort.modifySubject(subject)
            .orElseThrow(() -> new RuntimeException("Failed to change para"));
    }
}







