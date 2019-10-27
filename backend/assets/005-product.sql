create table product
(
	id serial not null
		constraint product_pk
			primary key,
	name varchar(256) not null,
	description varchar(256) not null,
	weight double precision not null,
	lot_id integer not null
		constraint product_lot_id_fk
			references lot
);

alter table product owner to postgres;

create unique index product_id_uindex
	on product (id);

