package com.para.demo.port;

import java.util.List;
import java.util.Optional;

import com.para.demo.model.Note;

public interface NotePort {

    Optional<Note> addNote(Note note);

    Optional<Note> getNote(Long noteId);

    Optional<Note> modifyNote(Note modifiedNote);

    void removeNote(Long noteId);

    List<Note> noteList(String title);

    List<Note> notes(Long subjectId);

}


