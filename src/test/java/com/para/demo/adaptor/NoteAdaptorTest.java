package com.para.demo.adaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.para.demo.repository.NoteRepository;

@Import({NoteAdaptor.class, NoteRepository.class})
@JdbcTest
@Testcontainers
public class NoteAdaptorTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = 
            new PostgreSQLContainer<>(
                DockerImageName.parse("postgres:17.2-alpine")
            );
    
    @Autowired
    NoteAdaptor noteAdaptor;

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void loadAdaptorTest() {
    }

    @Test
    public void noteCrudTest() {
        var note = new Note(null,
                "economic",
                "content");
        var addedNote = noteAdaptor.addNote(note);
        var newNoteId = addedNote.get().noteId();
        assertThat(addedNote.get().noteId()).isNotNull();
        assertThat(addedNote.get().title()).isEqualTo("economic");
       
        var modifiedNote = new Note(
                newNoteId, 
                "math", 
                note.content());
        var modifiedResult = noteAdaptor.modifyNote(modifiedNote);
        assertThat(modifiedResult.get().title()).isEqualTo("math");

        noteAdaptor.removeNote(newNoteId);

        var removedNote = noteAdaptor.getNote(newNoteId);
        assertThat(removedNote).isEmpty();
    }

    @Test
    public void noteList() {
        var searchTitle = "m";
        var results = noteAdaptor.noteList(searchTitle);
        assertThat(results.size()).isZero();
    }

    @Test
    public void notes() {
        var subjectId = 1L;
        var results = noteAdaptor.notes(subjectId);
        assertThat(results.size()).isZero();
    }
}
























