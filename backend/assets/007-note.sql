create table note
(
	id serial not null
		constraint note_pk
			primary key,
	date date not null,
	price_credit bigint,
	description_credit varchar(256),
	description_debit varchar(256),
	price_debit bigint,
	application_id integer not null
		constraint note_application_id_fk
			references application
);

alter table note owner to postgres;

create unique index note_id_uindex
	on note (id);

