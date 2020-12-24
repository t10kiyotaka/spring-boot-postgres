drop table if exists person;
create table person(
    id UUID not null primary key,
    name varchar(100) not null
);

create extension "uuid-ossp";
insert into person (id, name) values
    (uuid_generate_v4(), 'test name1'),
    (uuid_generate_v4(), 'test name2'),
    (uuid_generate_v4(), 'test name3');

