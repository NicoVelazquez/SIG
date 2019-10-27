create table client_product
(
	id serial not null
		constraint client_product_pk
			primary key,
	quantity integer not null,
	client_id integer not null
		constraint client_product_client_id_fk
			references client,
	product_id integer not null
		constraint client_product_product_id_fk
			references product
);

alter table client_product owner to postgres;

create unique index client_product_id_uindex
	on client_product (id);

