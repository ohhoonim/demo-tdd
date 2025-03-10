package com.para.demo.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.para.demo.model.ParaEnum;
import com.para.demo.model.Subject;
import com.para.demo.port.SubjectPort;
import com.para.demo.repository.SubjectRepository;

@Component
public class SubjectAdaptor implements SubjectPort {

    private final SubjectRepository subjectRepository;

    public SubjectAdaptor(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void registSubject(Long noteId, Long subjectId) {
        subjectRepository.registSubject(noteId, subjectId);
    }

    @Override
    public void removeSubject(Long noteId, Long subjectId) {
        subjectRepository.removeSubject(noteId, subjectId);
    }

    @Override
    public List<Subject> subjects(Long noteId) {
        return subjectRepository.subjects(noteId);
    }
// //////////////////////////////////////////////////////////////
    @Override
    public Optional<Subject> addSubject(Subject newSubject) {
        return subjectRepository.addSubject(newSubject); 
    }

    @Override
    public Optional<Subject> modifySubject(Subject modifiedSubject) {
        return subjectRepository.modifySubject(modifiedSubject);
    }

    @Override
    public void removeSubject(Long subjectId) {
        subjectRepository.removeSubject(subjectId);
    }

    @Override
    public Optional<Subject> getSubject(Long subjectId) {
        return subjectRepository.getSubject(subjectId);
    }
// //////////////////////////////////////////////////////////

    @Override
    public List<Subject> findSubjectList(String title) {
        return subjectRepository.subjectList(title);
    }




}

















