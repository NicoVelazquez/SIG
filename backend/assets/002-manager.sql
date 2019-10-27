create table manager
(
	id serial not null
		constraint manager_pk
			primary key,
	name varchar(256) not null,
	phone integer not null,
	email varchar(256) not null,
	password varchar(256) not null,
	address_id integer not null
		constraint manager_address_id_fk
			references address
);

alter table manager owner to postgres;

create unique index manager_id_uindex
	on manager (id);

