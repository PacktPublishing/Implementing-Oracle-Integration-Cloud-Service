
CREATE TABLE icsbook.Airports
(
airportId int primary key,
ident varchar2 (10),
airportType varchar2 (20),
airportName varchar2 (255),
latitude number (11,5),
longitude number (11,5),
elevation number (6),
continent varchar2(2),
country varchar2(3),
region varchar2(6),
muncipality varchar2(30),
scheduleFlights varchar2(5),
gpsCode varchar2(5),
IATACode varchar2(5) default null,
localcode varchar2(10),
homeLink varchar2(4000) default null,
wikipediaLink varchar2(4000) default null,
keywords varchar2(4000) default null,
icsStatus varchar2(4) default 'new' not null)
TABLESPACE icsbookspace
;


