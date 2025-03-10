create sequence if not exists seq_para_note start with 1;

create table if not exists para_note (
	note_id bigint not null default nextval('seq_para_note'),
	title varchar(255),
	content text,
	constraint pk_para_note primary key (note_id)
);

comment on table para_note is 'note';
comment on column para_note.note_id is 'id';
comment on column para_note.title is '제목';
comment on column para_note.content is '노트내용';


create sequence if not exists seq_para_subject start with 1; 

create table if not exists para_subject (
	subject_id bigint not null default nextval('seq_para_subject'),
	title varchar(255),
	content text,
	para varchar(20), 
	constraint pk_para_subject primary key (subject_id)
);
comment on table para_subject is 'subject';
comment on column para_subject.subject_id is 'id';
comment on column para_subject.title is '제목';
comment on column para_subject.content is '노트내용';
comment on column para_subject.para is '파라타입(ParaEnum)';

create table if not exists  para_note_subject (
	note_id bigint not null,
	subject_id bigint not null,
	constraint pk_para_note_subject primary key (note_id, subject_id),
	constraint fk_para_note_subject_note_id foreign key 
		(note_id) references para_note(note_id),
	constraint fk_para_note_subject_subject_id foreign key
		(subject_id) references para_subject(subject_id)
);

