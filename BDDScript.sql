DROP DATABASE IF EXISTS MaisonDL;
CREATE DATABASE MaisonDL;
USE MaisonDL;

CREATE TABLE Ligue
(
	NumL int(4) NOT NULL AUTO_INCREMENT,
	NomL varchar(50),
	primary key (NumL)
)ENGINE = INNODB;


INSERT INTO Ligue VALUES (1, 'Ligue de basket');
INSERT INTO Ligue VALUES (2, 'Ligue de volley-ball');
INSERT INTO Ligue VALUES (3, 'Ligue de tennis');
INSERT INTO Ligue VALUES (4, 'Ligue de natation');

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
	EstRoot boolean default 0,
	EstAdmin boolean default 0,
	NumLigue int(4) null,
	primary key (NumE),
	foreign key (NumLigue) references Ligue(NumL)
)ENGINE = INNODB;


INSERT INTO Employe VALUES (0,'root',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0, NULL);
INSERT INTO Employe VALUES (1,'DOLI','Prane','doli.prane@yahoo.fr','23 Rue Auguste Perret',75013,'Paris',NULL,NULL,0,1,1);
INSERT INTO Employe VALUES (2,'MONO','Poly','m.poly@gmail.com','75 Rue Pierre Demours',75017,'Paris',NULL,NULL,0,1,2);
INSERT INTO Employe VALUES (3,'FLEUR','Rose','fleur.rose@yahoo.com','22 Rue des Plantes',75012,'Paris',NULL,NULL,0,1,3);
INSERT INTO Employe VALUES (4,'STAN','Evelynn','evelynn.isthatbitch@gmail.com','179 Avenue Daumesnil',75012,'Paris',NULL,NULL,0,1,4);
INSERT INTO Employe VALUES (5,'ROBSON','Aleksander','robson.aleksander@hotmail.com','70 Rue Stephenson',75018,'Paris',NULL,NULL,0,0,1);
INSERT INTO Employe VALUES (6,'FREEMAN','Kiya','k.freeman@gmail.com','244 Rue Championnet',75018,'Paris',NULL,NULL,0,0,1);
INSERT INTO Employe VALUES (7,'FAULKNER','Luca','luca.faulkner@yahoo.fr','37 Rue du Louvre',75002,'Paris',NULL,NULL,0,0,1);
INSERT INTO Employe VALUES (8,'FERGUSON','Nafeesa','ferguson.nafeesa@gmail.com','19 Rue de la Monnaie',75001,'Paris',NULL,NULL,0,0,2);
INSERT INTO Employe VALUES (9,'HARPER','Lennon','lennonharper@gmail.com','10 Rue Tronchet',75008,'Paris',NULL,NULL,0,0,2);