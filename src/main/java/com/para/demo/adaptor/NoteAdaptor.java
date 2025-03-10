package com.para.demo.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.para.demo.model.Note;
import com.para.demo.port.NotePort;
import com.para.demo.repository.NoteRepository;

@Component
public class NoteAdaptor implements NotePort{

    private final NoteRepository noteRepository;

    public NoteAdaptor (NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Optional<Note> addNote(Note note) {
        return noteRepository.addNote(note);
    }

    @Override
    public Optional<Note> getNote(Long noteId) {
        return noteRepository.getNote(noteId);
    }

    @Override
    public Optional<Note> modifyNote(Note modifiedNote) {
        return noteRepository.modifyNote(modifiedNote);
    }

    @Override
    public void removeNote(Long noteId) {
        noteRepository.removeNote(noteId);
    }

    @Override
    public List<Note> noteList(String title) {
        return noteRepository.noteList(title);
    }

    @Override
    public List<Note> notes(Long subjectId) {
        return noteRepository.notes(subjectId);
    }
    
}
