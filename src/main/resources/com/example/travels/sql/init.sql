CREATE TABLE t_user (
	id int(6) PRIMARY KEY auto_increment,
	username VARCHAR(60),
	password VARCHAR(60),
	email VARCHAR(60)
);

CREATE TABLE t_province(
	id int(6) PRIMARY KEY auto_increment,
	name VARCHAR(60),
	tags VARCHAR(60),
	placecounts int(4)
);


CREATE TABLE t_place(
	id int(6) PRIMARY KEY auto_increment,
	name VARCHAR(60),
	picpath VARCHAR(100),
	hottime TIMESTAMP,
	hotticket DOUBLE(7,2),
	dimticket DOUBLE(7,2),
	placedes VARCHAR(300),
	provinceid int(6) REFERENCES t_province(id)
);