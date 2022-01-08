CREATE TYPE quals AS ENUM ('UEFA PRO', 'UEFA A', 'UEFA B', 'UEFA C','UEFA EY A','Grassroots C',
						  'Grassroots D', 'UEFA Futsal B',' UEFA Futsal C');

CREATE TABLE Coach
(
    IdCoach        SERIAL,
    Name           varchar(50) NOT NULL,
    Surname        varchar(50) NOT NULL,
    Qualifications quals NOT NULL,
    CONSTRAINT PK_trene PRIMARY KEY ( IdCoach )
);

INSERT INTO Coach(Name, Surname, Qualifications)
VALUES ('Adam','Adamski','UEFA B'),
       ('Bartosz','Bartoszewski','UEFA PRO');

CREATE TABLE League
(
    IdLeague SERIAL,
    Name     varchar(50) NOT NULL,
    CONSTRAINT PK_liga PRIMARY KEY ( IdLeague )
);

INSERT INTO League(Name)
VALUES ('Liga okregowa'),
       ('Mazowiecka Liga Juniorow Starszych');

CREATE TABLE Player
(
    IdPlayer  SERIAL,
    Surname   varchar(50) NOT NULL,
    BirthDate date NOT NULL,
    PESEL     bigint NOT NULL,
    Phone     varchar(50) NULL,
    Adress    varchar(50) NULL,
    Name      varchar(50) NULL,
    CONSTRAINT PK_zawodnik PRIMARY KEY ( IdPlayer )
);

INSERT INTO Player(Surname,BirthDate,PESEL,Phone,Adress,Name)
VALUES ('Cackowski','1982-04-30','82043012345',Null,Null,'Cezary'),
       ('Dadacz','2000-01-01','00210101011',Null,Null,'Dariusz'),
       ('Fabijanski','2000-02-02','0022020201','123123123','Warszawa','Filip'),
       ('Lewandowski','2000-03-03','0023030303','234234234','Warszawa Gorczewska 113','Robert'),
       ('Krychowiak','1991-10-13','9119131313','432432432','Zielonki, Zielona 20','Wojciech'),
       ('Zielinski','1993-07-07','93070712321','234345456','Sulejowek, Warszawska 210','Piotr'),
       ('Wojcik','1997-04-23','97042372445','890890098','Warszawa, Plac Defilad 11','Pawel');

CREATE TABLE "Position"
(
    IdPosition SERIAL,
    Name       varchar(50) NOT NULL,
    CONSTRAINT PK_pozycjazawodnika PRIMARY KEY ( IdPosition )
);

INSERT INTO "Position"(Name)
VALUES ('Bramkarz'),
       ('Obronca'),
       ('Pomocnik'),
       ('Napastnik');

CREATE TABLE Season
(
    IdSezonu  SERIAL,
    StartDate date NOT NULL,
    EndDate   date NOT NULL,
    CONSTRAINT PK_sezon PRIMARY KEY ( IdSezonu )
);

INSERT INTO Season(StartDate,EndDate)
VALUES ('2020-07-01','2021-06-30'),
       ('2021-07-01','2022-06-30');

CREATE TABLE Team
(
    IdTeam SERIAL,
    Name   varchar(50) NULL,
    Class  varchar(50) NULL,
    CONSTRAINT PK_druzyna PRIMARY KEY ( IdTeam )
);

INSERT INTO Team(Name,Class)
VALUES ('KTS Weszlo','Seniorzy'),
       ('Legia Warszawa','2000'),
       ('Drukarz Warszawa','Seniorzy'),
       ('Drukarz Warszawa','2000'),
       ('Agape Białołęka','2001'),
       ('Legia Warszawa','Seniorzy'),
       ('GKP Targówek','Seniorzy');

CREATE TABLE "Match"
(
    IdMatch    SERIAL,
    Opponent   varchar(50) NOT NULL,
    Place      varchar(50) NOT NULL,
    GoalsHome  int NOT NULL,
    GoalsAway  int NOT NULL,
    MatchDate  date NOT NULL,
    Host       boolean NOT NULL,
    IdHostTeam bigint NOT NULL,
    IdAwayTeam bigint NOT NULL,
    CONSTRAINT PK_mecz PRIMARY KEY ( IdMatch ),
    CONSTRAINT FK_215 FOREIGN KEY ( IdHostTeam ) REFERENCES Team ( IdTeam ),
    CONSTRAINT FK_218 FOREIGN KEY ( IdAwayTeam ) REFERENCES Team ( IdTeam )
);

INSERT INTO "Match"(Opponent,Place,GoalsHome,GoalsAway,MatchDate,Host,IdHostTeam,IdAwayTeam)
VALUES ('Legia Warszawa','Łazienkowska 3',0,2,'2021-04-20',False,2,1),
       ('Drukarz Warszawa','Park Praski',0,0,'2021-04-27',False,3,1),
       ('Legia Warszawa','Stadion Okęcia',1,1,'2021-05-03',True,1,2),
       ('GKP Targówek','Stadion Okęcia',3,0,'2021-05-10',True,1,7),
       ('Legia Warszawa','Stadion Okęcia',0,6,'2021-05-17',True,1,6);

CREATE TABLE TrainingType
(
    IdType      SERIAL,
    TypTreningu varchar(50) NOT NULL,
    CONSTRAINT PK_typtreningu PRIMARY KEY ( IdType )
);

INSERT INTO TrainingType(TypTreningu)
VALUES ('Kondycyjny'),('Bramkarski'),('Obrońców'),('Ogólny');

CREATE TABLE Training
(
    IdTraining   SERIAL,
    IdCoach      bigint NOT NULL,
    IdTeam       bigint NOT NULL,
    TrainingDate date NOT NULL,
    IdType       bigint NOT NULL,
    CONSTRAINT PK_trening PRIMARY KEY ( IdTraining ),
    CONSTRAINT FK_148 FOREIGN KEY ( IdCoach ) REFERENCES Coach ( IdCoach ),
    CONSTRAINT FK_151 FOREIGN KEY ( IdTeam ) REFERENCES Team ( IdTeam ),
    CONSTRAINT FK_168 FOREIGN KEY ( IdType ) REFERENCES TrainingType ( IdType )
);

INSERT INTO Training(IdCoach,IdTeam,TrainingDate,IdType)
VALUES (1,1,'2021-07-23',4),
       (1,1,'2021-07-24',4),
       (1,1,'2021-07-25',4);

CREATE TABLE TeamLeagueSeason
(
    IdTeamLeagueSeason bigint NOT NULL,
    IdLeague           bigint NOT NULL,
    IdTeam             bigint NOT NULL,
    IdSezonu           bigint NOT NULL,
    CONSTRAINT PK_druzynaligasezon PRIMARY KEY ( IdTeamLeagueSeason ),
    CONSTRAINT FK_182 FOREIGN KEY ( IdLeague ) REFERENCES League ( IdLeague ) ON DELETE SET NULL,
    CONSTRAINT FK_185 FOREIGN KEY ( IdTeam ) REFERENCES Team ( IdTeam ) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_194 FOREIGN KEY ( IdSezonu ) REFERENCES Season ( IdSezonu ) ON DELETE SET NULL
);

INSERT INTO TeamLeagueSeason (IdLeague,IdTeam,IdSezonu)
VALUES (1,1,1),
       (1,3,1),
       (1,7,1);

CREATE TABLE PlayerTeam
(
    IdPlayer     bigint NOT NULL,
    IdTeam       bigint NOT NULL,
    ContractTo   date NOT NULL,
    ContractFrom date NOT NULL,
    CONSTRAINT PK_zawodnikdruzyna PRIMARY KEY ( IdPlayer, IdTeam ),
    CONSTRAINT FK_141 FOREIGN KEY ( IdPlayer ) REFERENCES Player ( IdPlayer ) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT FK_144 FOREIGN KEY ( IdTeam ) REFERENCES Team ( IdTeam ) ON DELETE SET NULL ON UPDATE SET NULL
);

INSERT INTO PlayerTeam (IdPlayer,IdTeam,ContractTo,ContractFrom)
VALUES (1,1,'2020-07-01','2024-06-30'),
       (2,1,'2020-07-01','2023-06-30'),
       (3,1,'2020-07-01','2025-06-30'),
       (4,1,'2020-07-01','2023-06-30'),
       (5,1,'2020-07-01','2024-06-30'),
       (6,1,'2020-07-01','2026-06-30'),
       (7,1,'2020-07-01','2024-06-30'),
       (8,1,'2020-07-01','2025-06-30');

CREATE TABLE PlayerMatchStats
(
    IdPlayer       bigint NOT NULL,
    IdMatch        bigint NOT NULL,
    AttendanceReal boolean NOT NULL,
    MinutesPlayed  int NULL,
    GoalsScored    int NULL,
    YellowCards    int NULL,
    RedCards       int NULL,
    CONSTRAINT PK_zawodnikmeczstatystyki PRIMARY KEY ( IdPlayer, IdMatch ),
    CONSTRAINT FK_93 FOREIGN KEY ( IdPlayer ) REFERENCES Player ( IdPlayer ) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_97 FOREIGN KEY ( IdMatch ) REFERENCES "Match" ( IdMatch ) ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO PlayerMatchStats(IdPlayer,IdMatch,AttendaceReal,MinutesPlayed,GoalsScored,YellowCards,RedCards)
VALUES (1,1,True,90,0,1,0),
       (2,1,True,90,0,2,0),
       (3,2,False,Null,Null,Null,Null),
       (3,3,True,45,1,0,0);

CREATE TABLE PlayerPosition
(
    PositionNormal varchar(50) NOT NULL,
    IdPosition     int NOT NULL,
    IdPlayer       bigint NOT NULL,
    IdMatch        bigint NOT NULL,
    CONSTRAINT FK_139 FOREIGN KEY ( IdPosition ) REFERENCES "Position" ( IdPosition ),
    CONSTRAINT FK_232 FOREIGN KEY ( IdPlayer, IdMatch ) REFERENCES PlayerMatchStats ( IdPlayer, IdMatch )
);

INSERT INTO PlayerPosition (PositionNormal,IdPosition,IdPlayer,IdMatch)
VALUES ('Prawoskrzydłowy',4,1,1);

CREATE TABLE CoachTeam
(
    IdCoach bigint NOT NULL,
    IdTeam  bigint NOT NULL,
    CONSTRAINT PK_trenerdruzyna PRIMARY KEY ( IdCoach, IdTeam ),
    CONSTRAINT FK_104 FOREIGN KEY ( IdCoach ) REFERENCES Coach ( IdCoach ) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT FK_108 FOREIGN KEY ( IdTeam ) REFERENCES Team ( IdTeam ) ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO CoachTeam (IdCoach,IdTeam)
VALUES (1,1);

CREATE TABLE AttendanceTraining
(
    IdAttendance        SERIAL,
    IdTraining          bigint NOT NULL,
    IdPlayer            bigint NOT NULL,
    AttendanceConfirmed boolean NOT NULL,
    AttendaceReal       boolean NOT NULL,
    "Date"                date NOT NULL,
    CONSTRAINT PK_listaobecnoscitrening PRIMARY KEY ( IdAttendance ),
    CONSTRAINT FK_158 FOREIGN KEY ( IdTraining ) REFERENCES Training ( IdTraining ),
    CONSTRAINT FK_161 FOREIGN KEY ( IdPlayer ) REFERENCES Player ( IdPlayer )
);

INSERT INTO AttendanceTraining (IdTraining,IdPlayer,AttendanceConfirmed,AttendaceReal,"Date")
VALUES (1,1,True,False,Null);

--recznie stworzone indeksy

CREATE UNIQUE NONCLUSTERED INDEX PlayerIndex ON Player
(PESEL)
WHERE PESEL IS NOT NULL;

CREATE UNIQUE NONCLUSTERED INDEX TeamIndex ON Team
(
 Name,
 Class
)
WHERE Name IS NOT NULL AND Class IS NOT NULL;

--automatycznie stworzone indeksy

CREATE INDEX fkIdx_159 ON AttendanceTraining
(
 IdTraining
);

CREATE INDEX fkIdx_162 ON AttendanceTraining
(
 IdPlayer
);

CREATE INDEX fkIdx_105 ON CoachTeam
(
 IdCoach
);

CREATE INDEX fkIdx_109 ON CoachTeam
(
 IdTeam
);

CREATE INDEX fkIdx_216 ON "Match"
(
 IdHostTeam
);

CREATE INDEX fkIdx_219 ON "Match"
(
 IdAwayTeam
);

CREATE INDEX fkIdx_94 ON PlayerMatchStats
(
 IdPlayer
);

CREATE INDEX fkIdx_98 ON PlayerMatchStats
(
 IdMatch
);

CREATE INDEX fkIdx_140 ON PlayerPosition
(
 IdPosition
);

CREATE INDEX fkIdx_233 ON PlayerPosition
(
 IdPlayer,
 IdMatch
);

CREATE INDEX fkIdx_142 ON PlayerTeam
(
 IdPlayer
);

CREATE INDEX fkIdx_145 ON PlayerTeam
(
 IdTeam
);

CREATE INDEX fkIdx_183 ON TeamLeagueSeason
(
 IdLeague
);

CREATE INDEX fkIdx_186 ON TeamLeagueSeason
(
 IdTeam
);

CREATE INDEX fkIdx_195 ON TeamLeagueSeason
(
 IdSezonu
);

CREATE INDEX fkIdx_149 ON Training
(
 IdCoach
);

CREATE INDEX fkIdx_152 ON Training
(
 IdTeam
);

CREATE INDEX fkIdx_169 ON Training
(
 IdType
);

CREATE OR REPLACE FUNCTION auto_data_training_fnc() RETURNS TRIGGER AS $$
DECLARE
BEGIN
IF tg_op = 'UPDATE' and NEW.attendanceconfirmed != OLD.attendanceconfirmed THEN
NEW.Date = CURRENT_DATE;
END IF;
IF tg_op = 'INSERT' THEN
NEW.Date = CURRENT_DATE;
END IF;
END;
$$ LANGUAGE 'plpgsql';


CREATE TRIGGER auto_data_training
    AFTER INSERT OR UPDATE ON attendancetraining
                        FOR EACH ROW
                        EXECUTE PROCEDURE auto_data_training_fnc();

CREATE OR REPLACE FUNCTION auto_red_card_fnc() RETURNS TRIGGER AS $$
DECLARE
BEGIN
IF NEW.yellowcards = 2 and NEW.redcards = 0 THEN
SET NEW.redcards = 1;
END IF;
END;
$$ LANGUAGE 'plpgsql';


CREATE TRIGGER auto_red_card
    AFTER INSERT OR UPDATE ON playermatchstats
                        FOR EACH ROW
                        EXECUTE PROCEDURE auto_red_card_fnc();