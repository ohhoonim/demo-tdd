package com.para.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.para.demo.model.Note;
import com.para.demo.model.ParaEnum;
import com.para.demo.model.Subject;
import com.para.demo.port.NotePort;
import com.para.demo.port.SubjectPort;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @InjectMocks
    SubjectService subjectService;

    @Mock
    SubjectPort subjectPort;

    @Mock
    NotePort notePort;

    @Test
    public void subjectCrudTest() {
        // add
        var newSubject = new Subject(
                null,
                "title",
                "content", ParaEnum.AREA);

        var addedSubject = Optional.of(new Subject(
                1L,
                "title",
                "content", ParaEnum.AREA));

        when(subjectPort.addSubject(any())).thenReturn(addedSubject);

        assertThat(subjectService.addSubject(newSubject))
                .isInstanceOf(Subject.class);

        // modify
        var mockModifiedSubject = Optional.of(new Subject(
                1L,
                "changed title",
                "content", ParaEnum.RESOURCE));

        when(subjectPort.modifySubject(any()))
                .thenReturn(mockModifiedSubject);

        var modifiedSubject = new Subject(
                1L,
                "changed title",
                "content", ParaEnum.RESOURCE);

        assertThat(subjectService.modifySubject(modifiedSubject))
                .isInstanceOf(Subject.class);

        // remove
        subjectService.removeSubject(1L);
        verify(subjectPort, times(1))
                .removeSubject(1L);

    }

    // usecase에 대해 조금 더 생각해보자 ...
    // 주제별로 노트 목록을 가져와야한다.
    // PARA 변경이 가능해야한다.

    @Test
    public void notesInSubjectTest() {
        var subjectId = 1L;

        when(notePort.notes(subjectId))
                .thenReturn(List.of(
                        new Note(1L, "title", "content"),
                        new Note(2L, "title", "content")));

        assertThat(subjectService.noteList(subjectId))
                .hasSize(2);
    }

    @Test
    public void changeParaTest() {
        var originalSubject = new Subject(
                1L, "title", "content", ParaEnum.AREA);

        var targetSubject = new Subject(
                1L, "title", "content", ParaEnum.RESOURCE);

        when(subjectPort.modifySubject(any()))
                .thenReturn(Optional.of(targetSubject));

        assertThat(subjectService.changePara(originalSubject).para())
                .isEqualTo(ParaEnum.RESOURCE);
    }

}

