DROP DATABASE IF EXISTS MaisonDL;
DROP TABLE IF EXISTS Ligue;
DROP TABLE IF EXISTS Employe;

CREATE DATABASE MaisonDL;
USE MaisonDL;

CREATE TABLE Ligue
(
	NumL int not null,
	NomL varchar(25),
	constraint pk_Ligue primary key (idLigue)
)ENGINE = INNODB;

CREATE TABLE Employe 
(
	NumE int not null,
	NomE varchar(25),
	PrenomE varchar(25),
	CourrielE varchar(25),
	AdrueE varchar(25),
	CPE int,
	VilleE varchar(25),
	DateAE date,
	DateDE date,
	EstRoot boolean,
	EstAdmin boolean,
	NumL int null references Ligue(NumL),
	constraint pk_Employe primary key (idEmploye)
)ENGINE = INNODB;

