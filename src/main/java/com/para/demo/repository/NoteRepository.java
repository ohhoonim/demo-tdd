package com.para.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.para.demo.model.Note;

@Repository("noteRepository")
public class NoteRepository {

    private final JdbcClient jdbcClient;

    public NoteRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Note> addNote(Note note) {
        var sql = """
                insert into para_note (title, content)
                values (:title, :content)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql).params(noteParamMap.apply(note))
                .update(keyHolder);

        return this.getNote((Long) keyHolder.getKeys().get("note_Id"));
    }

    private final Function<Note, Map<String, Object>> noteParamMap = note -> {
        Map<String, Object> map = new HashMap<>();
        map.put("noteId", note.noteId());
        map.put("title", note.title());
        map.put("content", note.content());

        return map;
    };

    private final RowMapper<Note> noteRowMapper = (rs, idx) -> {
        return new Note(
                rs.getLong("note_id"),
                rs.getString("title"),
                rs.getString("content"));
    };

    public Optional<Note> getNote(Long noteId) {
        var sql = """
                select note_id, title, content
                from para_note
                where note_id = :noteId
                """;
        return jdbcClient.sql(sql)
                .param("noteId", noteId)
                .query(noteRowMapper).optional();
    }

    public Optional<Note> modifyNote(Note modifiedNote) {
        var sql = """
                update para_note
                set
                    title = :title
                    , content = :content
                where note_id = :noteId
                """;
        jdbcClient.sql(sql)
                .params(noteParamMap.apply(modifiedNote))
                .update();

        return this.getNote(modifiedNote.noteId());
    }

    public void removeNote(Long noteId) {
        var sql = """
                delete from para_note where note_id = :noteId
                """;
        jdbcClient.sql(sql)
                .param("noteId", noteId)
                .update();
    }

    public List<Note> noteList(String title) {
        var sql = """
                select note_id, title, content
                from para_note
                where title like '%:title%'
                """;
        return jdbcClient.sql(sql)
                .param("title", title)
                .query(noteRowMapper).list();
    }

    public List<Note> notes(Long subjectId) {
        var sql = """
                select
                from
                    para_note n
                join para_note_subject ns
                on
                    n.note_id = ns.note_id
                where
                    ns.subject_id = :subjectId
                """;
        return jdbcClient.sql(sql)
                .param("subjectId", subjectId)
                .query(noteRowMapper).list();
    }

}
