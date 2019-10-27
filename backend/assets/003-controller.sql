create table controller
(
	id serial not null
		constraint controller_pk
			primary key,
	name varchar(256) not null,
	phone integer not null,
	email varchar(256) not null,
	password varchar(256) not null,
	address_id integer not null
		constraint controller_address_id_fk
			references address
);

alter table controller owner to postgres;

create unique index controller_id_uindex
	on controller (id);

