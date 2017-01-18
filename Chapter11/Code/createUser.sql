-- COMMIT ;


DROP TABLESPACE icsbookspace INCLUDING CONTENTS AND DATAFILES;

-- DROP USER icsbook ;

CREATE TABLESPACE
   icsbookspace
datafile 
   '/u01/app/oracle/oradata/demos/icsbook.dbf'
size 
   60m
autoextend on 
next 20m 
maxsize 200m;

CREATE USER icsbook IDENTIFIED BY icsbook
       DEFAULT TABLESPACE icsbookspace  
       TEMPORARY TABLESPACE temp
       QUOTA UNLIMITED ON icsbookspace ;

GRANT CONNECT, DBA to icsbook ;

GRANT create table, create sequence to icsbook ;

GRANT all privileges to icsbook ;