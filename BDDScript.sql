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
	NumE int(4) not null AUTO_INCREMENT,
	NomE varchar(50) null,
	PrenomE varchar(50) null,
	CourrielE varchar(50) null,
	PasswordE varchar(50) null,
	DAE date null,
	DDE date null,
	EstRoot boolean default 0,
	EstAdmin boolean default 0,
	NumLigue int(4) null,
	primary key (NumE),
	foreign key (NumLigue) references Ligue(NumL)
)ENGINE = INNODB;


INSERT INTO Employe VALUES (1,'root',NULL,NULL,NULL,NULL,NULL,1,0, NULL);
INSERT INTO Employe VALUES (2,'DOLI','Prane','doli.prane@yahoo.fr',NULL,NULL,NULL,0,1,1);
INSERT INTO Employe VALUES (3,'MONO','Poly','m.poly@gmail.com',NULL,NULL,NULL,0,1,2);
INSERT INTO Employe VALUES (4,'FLEUR','Rose','fleur.rose@yahoo.com',NULL,NULL,NULL,0,1,3);
INSERT INTO Employe VALUES (5,'STAN','Evelynn','evelynn.isthatbitch@gmail.com',NULL,NULL,NULL,0,1,4);
INSERT INTO Employe VALUES (6,'ROBSON','Aleksander','robson.aleksander@hotmail.com',NULL,NULL,NULL,0,0,1);
INSERT INTO Employe VALUES (7,'FREEMAN','Kiya','k.freeman@gmail.com',NULL,NULL,NULL,0,0,2);
INSERT INTO Employe VALUES (8,'FAULKNER','Luca','luca.faulkner@yahoo.fr',NULL,NULL,NULL,0,0,3);
INSERT INTO Employe VALUES (9,'FERGUSON','Nafeesa','ferguson.nafeesa@gmail.com',NULL,NULL,NULL,0,0,4);