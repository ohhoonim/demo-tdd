package com.para.demo.api;

import static org.mockito.ArgumentMatchers.any;
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
import com.para.demo.service.SubjectUsecase;

@Import(SubjectUsecase.class)
@WebMvcTest(ParaController.class)
public class ParaControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    SubjectUsecase subjectService;

    @Test
    public void addSubject() throws JsonProcessingException {
        var newSubject = new Subject(null, "javascript", "grammar", ParaEnum.AREA);
        var result = new Subject(1L, "javascript", "grammar", ParaEnum.AREA);

        when(subjectService.addSubject(any())).thenReturn(result);

        mockMvcTester.post()
                .uri("/para/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSubject))
                .assertThat().apply(print())
                .hasStatusOk();

    }

    @Test
    public void modifySubject() throws JsonProcessingException {
        var modifySubject = new Subject(1L,
                "javascript",
                "grammar",
                ParaEnum.RESOURCE); // 이부분이 바뀌어 파라미터로 들어와야함

        var result = new Subject(1L,
                "javascript",
                "grammar",
                ParaEnum.RESOURCE);
        when(subjectService.modifySubject(any())).thenReturn(result);

        mockMvcTester.post()
                .uri("/para/modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifySubject))
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyJson().extractingPath("$.para")
                .isEqualTo("RESOURCE");

    }

    @Test
    public void removeSubject() {
        mockMvcTester.post()
                .uri("/para/1/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .assertThat().apply(print())
                .hasStatusOk()
                .bodyText().isEqualTo("success");
    }

    @Test
    public void getSubject() {

        var result = new Subject(1L,
                "javascript",
                "grammar",
                ParaEnum.AREA);

        when(subjectService.getSubject(any())).thenReturn(result);

        mockMvcTester.get()
                .uri("/para/1")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk();

    }

    @Test
    public void subjectList() {
        var resultList = List.of(
                new Subject(1L,
                        "javascript",
                        "grammar",
                        ParaEnum.AREA),
                new Subject(2L,
                        "java",
                        "collection",
                        ParaEnum.RESOURCE));

        when(subjectService.subjectList(any()))
                .thenReturn(resultList);

        mockMvcTester.post()
                .uri("/para/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("java")
                .assertThat().apply(print())
                .hasStatusOk();

    }

    @Test
    public void noteList() {
        var notes = List.of(
                new Note(1L, "math", "none"),
                new Note(2L, "economi", "theory"));
        when(subjectService.noteList(any())).thenReturn(notes);

        mockMvcTester.post()
                .uri("/para/1/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("") // get으로 해도 될 듯...
                .assertThat().apply(print())
                .hasStatusOk().bodyJson().extractingPath("$[0].title")
                .isEqualTo("math");

    }

    @Test
    public void changePara() throws JsonProcessingException {
        var changedSubject = new Subject(1L,
                "javascript",
                "grammar",
                ParaEnum.RESOURCE); // 이부분이 바뀌어 파라미터로 들어와야함

        when(subjectService.modifySubject(any()))
            .thenReturn(changedSubject);

        mockMvcTester.post()
                .uri("/para/change")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changedSubject))
                .assertThat().apply(print())
                .hasStatusOk();

    }

}
