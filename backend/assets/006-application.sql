create table application
(
	id serial not null
		constraint application_pk
			primary key,
	client_id integer not null
		constraint application_client_id_fk
			references client,
	cost integer,
	state varchar(256) not null,
	description varchar(256),
	observation varchar(256),
	date date,
	operator_acceptance_date date,
	collection_date date,
	client varchar(256)
);

alter table application owner to postgres;

create unique index application_id_uindex
	on application (id);

