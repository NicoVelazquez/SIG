create table client
(
	id serial not null
		constraint client_pk
			primary key,
	name varchar(256) not null,
	phone integer not null,
	email varchar(256) not null,
	password varchar(256) not null,
	address_id integer not null
		constraint client_address_id_fk
			references address
);

alter table client owner to postgres;

create unique index client_id_uindex
	on client (id);

