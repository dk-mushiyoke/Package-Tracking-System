create table customer (
	cid				numeric(10,0),
	name			varchar(20) not null,
	phone			numeric(10,0),
	type			varchar(10),
	primary key (cid)
);

create table account (
	aid				numeric(10,0),
	cid		numeric(10,0),
	type	varchar(10) check (type in ('credit','debit')),
	primary key (aid),
	foreign key (cid) references customer on delete cascade
);

create table transaction (
	tid				numeric(10,0),
	aid				numeric(10,0),
	time			timestamp,
	amount			numeric(8,2),
	primary key (tid),
	foreign key (account_id) references account on delete cascade
);

create table package (
	pid				numeric(10,0),
	weight			numeric(6,2),
	speed			varchar(10),
	primary key (pid)
);

create table hazardous (
	pid				numeric(10,0),
	content			varchar(20),
	primary key (pid),
	foreign key (pid) references package on delete cascade
);

create table international (
	pid				numeric(10,0),
	content			varchar(20),
	value			numeric(10,2),
	primary key (pid),
	foreign key (pid) references package on delete cascade
);

create table service (
	description		varchar(10),
	price			numeric(6,2),
	primary key (description, price)
);

create table tracking (
	tnum			numeric(18,0),
	pid				numeric(10,0),
	time			timestamp,
	activity		varchar(20),
	lid				numeric(6,0),
	primary key (tnum, time),
	foreign key (pid) references package on delete cascade
	foreign key (lid) references location on delete set null
);

create table location (
	lid				numeric(6,0),
	type			varchar(10) check (type in ('truck','aircraft','warehouse','store','household')) not null,
	street			varchar(50),
	city			varchar(20),
	state			varchar(20),
	zip				numeric(8,0),
	country			varchar(3),
	primary key (lid),
	unique(lid, type)
);

create table vehicle (
	vid				numeric(6,0),
	type			varchar(10) check (type in ('truck','aircraft')) not null,
	plate_num		varchar(10),
	primary key (vid),
	unique (vid, type)
);

create table building (
	bid				numeric(6,0),
	type			varchar(10) check (type in ('warehouse','store','household')) not null,
	name			varchar(15),
	primary key (bid),
	unique (bid, type)
);

create table payment (
	pid				numeric(10,0),
	tid				numeric(10,0),
	primary key (pid),
	foreign key (pid) references package on delete cascade,
	foreign key (tid) references transaction on delete set null
);

create table sender (
	pid				numeric(10,0),
	cid				numeric(10,0),
	street			varchar(50),
	city			varchar(20),
	state			varchar(20),
	zip				numeric(8,0),
	country			varchar(3),
	primary key (pid),
	foreign key (pid) references package on delete cascade,
	foreign key (cid) references customer on delete set null
);

create table receiver (
	pid				numeric(10,0),
	cid				numeric(10,0),
	street			varchar(50),
	city			varchar(20),
	state			varchar(20),
	zip				numeric(8,0),
	country			varchar(3),
	exp_dtime		timestamp,
	primary key (pid),
	foreign key (pid) references package on delete cascade,
	foreign key (cid) references customer on delete set null
);

create table regular_price(
	pid				numeric(10,0),
	description		varchar(10),
	price			numeric(6,2),
	primary key (pid),
	foreign key (pid) references package on delete cascade,
	foreign key (description,price) references service on delete set null
);

create table negotiated_price (
	cid				numeric(10,0),
	description		varchar(10),
	price			numeric(6,2),
	primary key (cid,description,price),
	foreign key (cid) references customer on delete cascade,
	foreign key (description,price) references service on delete set null
);

