package com.para.demo.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.para.demo.model.Note;
import com.para.demo.model.Subject;
import com.para.demo.service.SubjectUsecase;

@RestController
public class ParaController {

    private final SubjectUsecase subjectService;

    public ParaController(SubjectUsecase subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/para/add")
    public Subject addSubject(@RequestBody Subject newSubject) {
        return subjectService.addSubject(newSubject);
    }

    @PostMapping("/para/modify")
    public Subject modifySubject(@RequestBody Subject modifiedSubject) {
        return subjectService.modifySubject(modifiedSubject);
    }

    @PostMapping("/para/{subjectId}/remove")
    public String removeSubject(@PathVariable Long subjectId) {
        subjectService.removeSubject(subjectId);
        return "success";
    }

    @GetMapping("/para/{subjectId}")
    public Subject getSubject(@PathVariable Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @PostMapping("/para/list")
    public List<Subject> subjectList(@RequestBody String title) {
        return subjectService.subjectList(title);
    }

    @PostMapping("/para/{subjectId}/notes")
    public List<Note> noteList(@PathVariable Long subjectId) {
        return subjectService.noteList(subjectId);
    }

    @PostMapping("/para/change")
    public Subject changePara(@RequestBody Subject modifiedPara) {
        return subjectService.changePara(modifiedPara);
    }

}
