create table address
(
	id serial not null
		constraint address_pk
			primary key,
	country varchar(256) not null,
	city varchar(256) not null,
	street varchar(256) not null,
	street_height varchar(256) not null,
	description varchar(256) not null,
	cost integer not null
);

alter table address owner to postgres;

create unique index address_id_uindex
	on address (id);

