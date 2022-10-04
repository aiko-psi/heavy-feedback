create table SURVEYS (
    ID int primary key,
    title text
);

create table PAGES (
    ID int primary key,
    TITLE text,
    pos int not null
);

create table QUESTIONS (
    ID int primary key,
    TYPE text not null,
    IS_REQUIRED boolean,
    IS_REVERSE boolean,
    TITLE text not null,
    NOTE text,
    TOOLTIP text,
    PAGE_ID references PAGEs(ID)
);


