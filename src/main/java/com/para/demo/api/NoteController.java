package com.para.demo.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;
import com.para.demo.service.NoteUsecase;

@RestController
public class NoteController {

    private final NoteUsecase noteService;

    public NoteController(NoteUsecase noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note/add")
    public Note addNote(@RequestBody Note newNote) {
        return noteService.addNote(newNote);
    }

    @PostMapping("/note/modify")
    public Note modifyNote(@RequestBody Note modifiedNote) {
        return noteService.modifyNote(modifiedNote);
    }

    @PostMapping("/note/{noteId}/remove")
    public String removeNote(@PathVariable("noteId") Long noteId) {
        noteService.removeNote(noteId);
        // TODO exception 처리에 대한 공통기능 개발 필요
        return "success";
    }

    @GetMapping("/note/{noteId}")
    public Note getNote(@PathVariable Long noteId) {
        return noteService.getNote(noteId);
    }

    @PostMapping("/note/list")
    public List<Note> noteList(@RequestBody String title) {
        return noteService.noteList(title);
    }

    @PostMapping("/note/registSubject")
    // parameter를 requestbody로 받으면 ...
    // dto로 묶어주어야 할 것 같네요...
    public List<Subject> registSubject(@RequestBody ReqNoteSubject subject) {
        return noteService.registSubject(
                subject.noteId(),
                subject.subjectId());
    }

    public record ReqNoteSubject(
            Long noteId,
            Long subjectId) {
    }

    @PostMapping("/note/removeSubject")
    public List<Subject> removeSubject(@RequestBody ReqNoteSubject subject) {
        return noteService.removeSubject(
            subject.noteId(), 
            subject.subjectId());
    }

    @GetMapping("/note/{noteId}/subjects")
    public List<Subject> subjectList(@PathVariable Long noteId) {
        return noteService.subjectList(noteId);
    }

}
 // 다했네요. 끝...