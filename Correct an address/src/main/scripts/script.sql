/*delete
from countries;
delete
from states;
delete
from cities;*/

drop table countries cascade constraints;
drop table states cascade constraints;
drop table cities cascade constraints;
/*drop table my_addresses cascade constraints ;*/

create table countries
(
    country_id        number(3)    not null primary key,
    country_name      varchar2(150) not null,
    country_code_iso2 varchar2(5)
);
create table states
(
    state_id          number(5)    not null primary key,
    state_name        varchar2(200) not null,
    country_id        number(3) references countries (country_id),
    country_code_iso2 varchar2(5),
    state_code_iso2   varchar2(10)
);
create table cities
(
    city_id           number(6)    not null primary key,
    city_name         varchar2(300) not null,
    state_id          number(5) references states (state_id),
    state_code_iso2   varchar2(10),
    country_id        number(3) references countries (country_id),
    country_code_iso2 varchar2(5),
    longitude         double precision,
    latitude          double precision
);

insert into countries(country_id, country_name, country_code_iso2)
values (181, 'Romania', 'RO');

select * from countries order by country_id;
select count(*) from cities;