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

import com.para.demo.model.ParaEnum;
import com.para.demo.model.Subject;

@Repository
public class SubjectRepository {
    private final JdbcClient jdbcClient;

    public SubjectRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Subject> addSubject(Subject newSubject) {
        var sql = """
                insert into para_subject
                (title, content, para)
                values (:title, :content, :para)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(subjectMap.apply(newSubject))
                .update(keyHolder);
        Long newKey = (Long) (keyHolder.getKeys().get("subject_id"));
        return this.getSubject(newKey);
    }

    private final Function<Subject, Map<String, Object>> subjectMap = s -> {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("subjectId", s.subjectId());
        paramMap.put("title", s.title());
        paramMap.put("content", s.content());
        paramMap.put("para", s.para().toString());

        return paramMap;
    };

    public Optional<Subject> modifySubject(Subject modifiedSubject) {
        var sql = """
                update para_subject
                set
                    title = :title
                    , content = :content
                    , para = :para
                where
                    subject_id = :subjectId
                """;
        jdbcClient.sql(sql)
                .params(subjectMap.apply(modifiedSubject))
                .update();
        return this.getSubject(modifiedSubject.subjectId());
    }

    public void removeSubject(Long subjectId) {
        var sql = """
                delete from para_subject
                where subject_id = :subjectId

                """;
        jdbcClient.sql(sql)
                .param("subjectId", subjectId)
                .update();

    }

    public Optional<Subject> getSubject(Long subjectId) {
        var sql = """
                select subject_id, title, content, para
                from para_subject
                where subject_id = :subjectId
                """;
        return jdbcClient.sql(sql)
                .param("subjectId", subjectId)
                .query(subjectRowMapper)
                .optional();
    }

    private final RowMapper<Subject> subjectRowMapper = (rs, idx) -> {

        var para = ParaEnum.valueOf(rs.getString("para"));

        var subject = new Subject(
                rs.getLong("subject_id"),
                rs.getString("title"),
                rs.getString("content"),
                para);

        return subject;
    };

    public void registSubject(Long noteId, Long subjectId) {
        var sql = """
                insert into para_note_subject
                (note_id, subject_id)
                values (:noteId, :subjectId)
                """;
        jdbcClient.sql(sql)
                .param("noteId", noteId)
                .param("subjectId", subjectId)
                .update();
    }

    public void removeSubject(Long noteId, Long subjectId) {
        var sql = """
                delete from para_note_subject
                where note_id = :noteId
                    and subject_id = :subjectId
                """;
        jdbcClient.sql(sql)
                .param("noteId", noteId)
                .param("subjectId", subjectId)
                .update();
    }

    public List<Subject> subjects(Long noteId) {
        var sql = """
                select
                    s.subject_id
                    , s.title
                    , s.content
                    , s.para
                from para_subject s
                join para_note_subject ns
                on s.subject_id = ns.subject_id
                where ns.note_id = :noteId
                """;
        return jdbcClient.sql(sql)
                .param("noteId", noteId)
                .query(subjectRowMapper)
                .list();
    }

    public List<Subject> subjectList(String title) {
        var sql = """
                select subject_id, title, content, para
                from para_subject
                where title like '%:title%'
                """;
        return jdbcClient.sql(sql)
            .param("title", title)
            .query(subjectRowMapper)
            .list();
    }

}
