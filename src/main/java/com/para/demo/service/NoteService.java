package com.para.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;
import com.para.demo.port.NotePort;
import com.para.demo.port.SubjectPort;

@Service
public class NoteService implements NoteUsecase{

    private final NotePort notePort;
    private final SubjectPort subjectPort;

    public NoteService(NotePort notePort, SubjectPort subjectPort) {
        this.subjectPort = subjectPort;
        this.notePort = notePort;
    }
    @Override
    public Note addNote(Note newNote) {
        return notePort.addNote(newNote)
            .orElseThrow(() -> new RuntimeException("Failed to add note"));
    }

    @Override
    public Note modifyNote(Note modifiedNote) {
        return notePort.modifyNote(modifiedNote)
            .orElseThrow(() -> new RuntimeException("Failed to modify note"));
    }

    @Override
    public void removeNote(Long noteId) {
        notePort.removeNote(noteId);
    }

    @Override
    public Note getNote(Long noteId) {
        return notePort.getNote(noteId)
            .orElseThrow(() -> new RuntimeException("Failed to get note"));
    }

    @Override
    public List<Note> noteList(String title) {
        return notePort.noteList(title);
    }

    @Override
    public List<Subject> registSubject(Long noteId, Long subjectId) {
        subjectPort.registSubject(noteId, subjectId);
        return subjectPort.subjectList(noteId);
    }

    @Override
    public List<Subject> removeSubject(Long noteId, Long subjectId) {
        subjectPort.removeSubject(noteId, subjectId);
        return subjectPort.subjectList(noteId);
    }

    @Override
    public List<Subject> subjectList(Long noteId) {
        return subjectPort.subjectList(noteId);
    }
    
}
