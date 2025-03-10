package com.para.demo.adaptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.para.demo.model.Note;
import com.para.demo.model.ParaEnum;
import com.para.demo.model.Subject;
import com.para.demo.repository.NoteRepository;
import com.para.demo.repository.SubjectRepository;

@Import({ SubjectAdaptor.class, SubjectRepository.class, NoteRepository.class })
@JdbcTest
@Testcontainers
public class SubjectAdaptorTest {
    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:17.2-alpine"));

    @Autowired
    SubjectAdaptor subjectAdaptor;

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void subjectCrudTest() {
        var newSubject = new Subject(null,
                "java",
                "grammar",
                ParaEnum.AREA);

        var newSubjectResult = subjectAdaptor.addSubject(newSubject);

        assertThat(newSubjectResult.get().subjectId()).isNotNull();
        assertThat(newSubjectResult.get().para()).isEqualTo(ParaEnum.AREA);

        var subjectId = newSubjectResult.get().subjectId();

        var modifiedSubject = new Subject(
                subjectId,
                newSubject.title(),
                newSubject.content(),
                ParaEnum.RESOURCE);
        var modifiedSubejctResult = subjectAdaptor.modifySubject(modifiedSubject);
        assertThat(modifiedSubejctResult.get().para()).isEqualTo(ParaEnum.RESOURCE);

        subjectAdaptor.removeSubject(subjectId);
        var removedResult = subjectAdaptor.getSubject(subjectId);
        assertThat(removedResult).isEmpty();

    }

    @Test
    public void subjectsInNoteCrudTest() {
        // note
        var note = noteRepository.addNote(new Note(null, "변수", "변수사용은..."));
        var noteId = note.get().noteId();
        // subject
        var newSubject1 = new Subject(null, "java", "grammars", ParaEnum.AREA);
        var newSubject2 = new Subject(null, "javascript", "grammars", ParaEnum.RESOURCE);

        var java = subjectAdaptor.addSubject(newSubject1);
        var javascript = subjectAdaptor.addSubject(newSubject2);

        var javaKey = java.get().subjectId();
        var javascriptKey = javascript.get().subjectId();

        subjectAdaptor.removeSubject(noteId, javaKey);
        subjectAdaptor.removeSubject(noteId, javascriptKey);

        subjectAdaptor.registSubject(noteId, javaKey);
        subjectAdaptor.registSubject(noteId, javascriptKey);

        var subjectsInNote = subjectAdaptor.subjects(noteId);

        assertThat(subjectsInNote.size()).isEqualTo(2);

        subjectAdaptor.removeSubject(noteId, javaKey);
        var subjectsInNoteAfterRemove = subjectAdaptor.subjects(noteId);
        assertThat(subjectsInNoteAfterRemove.size()).isEqualTo(1);
    }

    @Test
    public void findSubjectList() {
        var results = subjectAdaptor.findSubjectList("M");

        assertThat(results.size()).isZero();
    }
    // 다시 전체 테스트 돌려볼께요..vscode에서는 가끔 이런일이...
    // 성공.
    // TDD로 전체 애플리케이션 코딩을 진행해봤습니다.  
    // 수고하셨구요. 
    // 코드리뷰시간에 다시 만나요~~~~~~~
}
