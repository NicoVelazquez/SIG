create table lot
(
	id serial not null
		constraint lot_pk
			primary key,
	name varchar(256) not null,
	creation_date date not null,
	price double precision not null,
	expiration_date date not null
);

alter table lot owner to postgres;

create unique index lot_id_uindex
	on lot (id);

