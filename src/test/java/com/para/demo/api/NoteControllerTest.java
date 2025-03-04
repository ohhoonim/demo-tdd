package com.para.demo.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.para.demo.model.Note;
import com.para.demo.model.ParaEnum;
import com.para.demo.model.Subject;
import com.para.demo.service.NoteUsecase;
import com.para.demo.api.NoteController.ReqNoteSubject;

@Import(NoteUsecase.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    NoteUsecase noteService;

    @Test
    public void addNoteTest() throws JsonProcessingException {
        var newNote = new Note(
                null,
                "economics",
                "micro economics");

        var addedNote = new Note(
                1L,
                "economics",
                "micro economics");
        when(noteService.addNote(any()))
                .thenReturn(addedNote);

        mockMvcTester.post()
                .uri("/note/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newNote))
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$.noteId")
                .isEqualTo(1);
    }

    @Test
    public void modifyNote() throws JsonProcessingException {
        var reqNote = new Note(
                1L,
                "economics",
                "macro economics");

        var addedNote = new Note(
                1L,
                "economics",
                "macro economics");
        when(noteService.modifyNote(any()))
                .thenReturn(addedNote);

        mockMvcTester.post()
                .uri("/note/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqNote))
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$.content")
                .isEqualTo("macro economics");
    }

    @Test
    public void removeNote() {
        mockMvcTester.post()
                .uri("/note/" + 1L + "/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk();
        verify(noteService, times(1)).removeNote(any());
    }

    @Test
    public void getNote() {
        var noteId = 2;
        var returnNote = new Note(
                2L,
                "economics",
                "macro economics");
        when(noteService.getNote(any()))
                .thenReturn(returnNote);
        mockMvcTester.get()
                .uri("/note/" + noteId)
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$.noteId")
                .isEqualTo(2);
    }

    @Test
    public void noteList() {
        var notes = List.of(
                new Note(1L, "math", "none"),
                new Note(2L, "economi", "theory"));
        when(noteService.noteList(any())).thenReturn(notes);

        mockMvcTester.post()
                .uri("/note/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("m")
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$")
                .asArray().hasSize(2);
    }

    @Test
    public void registSubject() throws JsonProcessingException {
        var reqNoteSubject = new ReqNoteSubject(
                1L, 2L);

        var subjects = List.of(
                new Subject(1L, "youtube", "none", ParaEnum.AREA),
                new Subject(2L, "javascript", "theory", ParaEnum.RESOURCE));

        when(noteService.registSubject(any(), any()))
                .thenReturn(subjects);

        mockMvcTester.post()
                .uri("/note/registSubject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqNoteSubject))
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$[0].title")
                .isEqualTo("youtube");
    }

    @Test
    public void removeSubject() throws JsonProcessingException {
        var reqNoteSubject = new ReqNoteSubject(
                1L, 2L);

        var subjects = List.of(
                new Subject(1L, "youtube", "none", ParaEnum.AREA));

        when(noteService.removeSubject(any(), any()))
                .thenReturn(subjects);

        mockMvcTester.post()
                .uri("/note/removeSubject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqNoteSubject))
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$[0].title")
                .isEqualTo("youtube");
    }

    @Test
    public void subjectList() {
        var subjects = List.of(
                new Subject(1L, "youtube", "none", ParaEnum.AREA),
                new Subject(2L, "javascript", "theory", ParaEnum.RESOURCE));

        when(noteService.subjectList(any())).thenReturn(subjects);

        mockMvcTester.get()
                .uri("/note/1/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk().bodyJson()
                .extractingPath("$")
                .asArray().hasSize(2);
    }


}
