create table product_application
(
	id serial not null
		constraint product_application_pk
			primary key,
	quantity integer not null,
	application_id integer not null
		constraint product_application_application_id_fk
			references application,
	product_id integer not null
		constraint product_application_product_id_fk
			references product,
	product_state varchar(256),
	accepted integer,
	good integer
);

alter table product_application owner to postgres;

create unique index product_application_id_uindex
	on product_application (id);

