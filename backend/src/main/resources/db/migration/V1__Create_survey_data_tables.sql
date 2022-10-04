create table PAGE (
    ID int primary key,
    TITLE varchar(100),
    pos int not null
);

create table QUESTION (
    ID int primary key,
    TYPE varchar(100) not null,
    IS_REQUIRED boolean,
    IS_REVERSE boolean,
    TITLE varchar(500) not null,
    NOTE varchar(500),
    TOOLTIP varchar(500),
    PAGE_ID references PAGE(ID)
);


