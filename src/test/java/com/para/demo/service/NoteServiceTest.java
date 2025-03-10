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
public class NoteServiceTest {

    @InjectMocks
    NoteService noteService;

    @Mock
    NotePort notePort;

    @Test
    public void noteCrudTest() {
        var noteId = 1L;
        // 처음엔 아이디가 없는 노트임
        var note = new Note(null,
                "title",
                "content");

        when(notePort.addNote(any())).thenReturn(
                Optional.of(new Note(noteId,
                        "title",
                        "content")));

        assertThat(noteService.addNote(any()))
                .isInstanceOf(Note.class);

        // 저장되면 아이디 부여됨
        var savedNote = new Note(noteId,
                "title",
                "content");
        when(notePort.getNote(any())).thenReturn(Optional.of(savedNote));
        ;
        assertThat(noteService.getNote(noteId))
                .isInstanceOf(Note.class);

        var modifiedNote = new Note(noteId,
                "modified title",
                "modified content");
        when(notePort.modifyNote(any()))
            .thenReturn(Optional.of(modifiedNote));
        assertThat(noteService.modifyNote(modifiedNote)).isInstanceOf(Note.class);

        noteService.removeNote(noteId);
        verify(notePort, times(1))
                .removeNote(noteId);

    }

    @Test
    public void noteListTest() {
        var title = "title";

        var mockList = List.of(
                new Note(1L, title, "content1"),
                new Note(2L, title, "content2"),
                new Note(3L, title, "content3"));
        when(notePort.noteList(any())).thenReturn(mockList);
        var noteList = noteService.noteList(title);

        assertThat(noteList).hasSize(3);
    }

    @Mock
    SubjectPort subjectPort;

    @Test
    public void subjectInNoteTest() {
        Long noteId = 1L;
        Long subjectId = 1L;

        // when
        var mockSubjectList = List.of(
                new Subject(1L, "java", "grammar", ParaEnum.AREA),
                new Subject(2L, "javascript", "lexical", ParaEnum.AREA),
                new Subject(3L, "docker", "hub", ParaEnum.RESOURCE));
        when(subjectPort.subjects(any())).thenReturn(mockSubjectList);

        assertThat(noteService.registSubject(1L, 1L))
                .hasSize(3);

        verify(subjectPort, times(1))
                .registSubject(noteId, subjectId);

        // remove subject when
        var mockRemovedList = List.of(
                new Subject(2L, "javascript", "lexical", ParaEnum.AREA),
                new Subject(3L, "docker", "hub", ParaEnum.RESOURCE));
        when(subjectPort.subjects(any())).thenReturn(mockRemovedList);
        assertThat(noteService.removeSubject(noteId, subjectId))
            .hasSize(2);

        var mockFinalList = List.of(
                new Subject(2L, "javascript", "lexical", ParaEnum.AREA),
                new Subject(3L, "docker", "hub", ParaEnum.RESOURCE));
        when(subjectPort.subjects(any())).thenReturn(mockFinalList);
        assertThat(noteService.subjectList(noteId))
            .hasSize(2);
    }

}













