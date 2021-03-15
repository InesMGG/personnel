DROP DATABASE IF EXISTS MaisonDL;
CREATE DATABASE MaisonDL;
USE MaisonDL;

CREATE TABLE Ligue
(
	NumL int(4) NOT NULL AUTO_INCREMENT,
	NomL varchar(50),
	primary key (NumL)
)ENGINE = INNODB;

CREATE TABLE Employe 
(
	NumE int(4) not null,
	NomE varchar(50) null,
	PrenomE varchar(50) null,
	CourrielE varchar(50) null,
	AdrueE varchar(50) null,
	CPE int(5) null,
	VilleE varchar(50) null,
	DAE date null,
	DDE date null,
	EstRoot varchar(3),
	EstAdmin varchar(3),
	NumLigue int(4) null,
	primary key (NumE),
	foreign key (NumLigue) references Ligue(NumL)
)ENGINE = INNODB;

