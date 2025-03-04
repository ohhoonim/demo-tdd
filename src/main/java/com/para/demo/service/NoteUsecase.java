package com.para.demo.service;

import java.util.List;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;

public interface NoteUsecase {
    public Note addNote(Note newNote);

    public Note modifyNote(Note modifiedNote);

    public void removeNote(Long noteId);

    public Note getNote(Long noteId);

    public List<Note> noteList(String title);

    // note에서 subject 등록, 제거
    public List<Subject> registSubject(Long noteId, Long subjectId);

    public List<Subject> removeSubject(Long noteId, Long subjectId);

    public List<Subject> subjectList(Long noteId);

}
