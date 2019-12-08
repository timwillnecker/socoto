CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.person (
	idcardnumber integer NOT NULL,
	salutation CHAR(5) DEFAULT ' ' NOT NULL,
	firstname CHAR (20),
	surname CHAR(20) DEFAULT ' ' NOT NULL,
	city CHAR(50) DEFAULT ' ' NOT NULL,
	place integer NOT NULL,
	street CHAR(75) DEFAULT ' ' NOT NULL,
	birthday Date,
    PRIMARY KEY (idcardnumber)
);
