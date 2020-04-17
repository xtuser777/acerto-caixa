create table caixa
(
	cxa_id serial not null primary key,
	cxa_saldo_inicial decimal not null,
	cxa_saldo_final decimal not null,
	cxa_status boolean not null
);

create table acerto
(
	act_id serial not null primary key,
	act_data timestamp not null,
	act_valor decimal not null,
	act_tipo integer not null,
	act_motivo varchar(50) null
);

create table movimento_caixa
(
	mc_id serial not null,
	cxa_id integer not null,
	mc_valor decimal not null,
	mc_tipo integer not null,
	act_id integer not null,
	primary key (mc_id, cxa_id),
	foreign key (cxa_id) references caixa(cxa_id),
	foreign key (act_id) references acerto(act_id)
);