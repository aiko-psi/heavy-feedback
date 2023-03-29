create table SURVEYS (
    ID int primary key,
    TITLE text
);

create table PAGES (
    ID int primary key,
    TITLE text,
    POS int not null,
    SURVEY_ID references SURVEYS(ID)
);

create table PAGE_ELEMENTS (
    ID int primary key,
    TYPE text not null,
    IS_REQUIRED boolean,
    IS_REVERSE boolean,
    TITLE text not null,
    NOTE text,
    TOOLTIP text,
    PAGE_ID references PAGES(ID)
);


