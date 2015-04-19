    drop table if exists AUTHOR;

    drop table if exists BOOK;

    drop table if exists BOOK_AUTHORS;

    drop table if exists BOOK_WAITING_USERS;

    drop table if exists USER;

    drop table if exists USERS_BOOKS;

    create table AUTHOR (
        id bigint not null auto_increment,
        bio varchar(255),
        dateOfBirth datetime,
        dateOfDeath datetime,
        firstName varchar(255),
        lastName varchar(255),
        primary key (id)
    );

    create table BOOK (
        id bigint not null auto_increment,
        ISBN10 varchar(255),
        ISBN13 varchar(255),
        checkOutDuration integer not null,
        dueDate datetime,
        publishDate datetime,
        shelf varchar(255),
        status integer,
        title varchar(255),
        primary key (id)
    );

    create table BOOK_AUTHORS (
        BOOK_id bigint not null,
        AUTHOR_id bigint not null
    );

    create table BOOK_WAITING_USERS (
        BOOK_ID bigint not null,
        USER_ID bigint not null
    );

    create table USER (
        ID bigint not null auto_increment,
        allowedCheckout bit not null,
        bookCheckoutLimit integer not null,
        dateOfBirth datetime,
        emailAddress varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        lateFees integer not null,
        passwordHash varchar(255),
        userLevel integer,
        userName varchar(255),
        primary key (ID)
    );

    create table USERS_BOOKS (
        BOOK_ID bigint,
        USER_ID bigint not null,
        primary key (USER_ID)
    );

    alter table BOOK_AUTHORS 
        add constraint FK_pospwn3uekp1cixbowtbimky6 
        foreign key (AUTHOR_id) 
        references AUTHOR (id);

    alter table BOOK_AUTHORS 
        add constraint FK_j4a2b5hgm0qg4arpohbdfxrdo 
        foreign key (BOOK_id) 
        references BOOK (id);

    alter table BOOK_WAITING_USERS 
        add constraint FK_epqmppa2qrm17q1o9qft4ek30 
        foreign key (USER_ID) 
        references USER (ID);

    alter table BOOK_WAITING_USERS 
        add constraint FK_1dq0vfkd576dc4so4jodpas8f 
        foreign key (BOOK_ID) 
        references BOOK (id);

    alter table USERS_BOOKS 
        add constraint FK_sv2rjvmhjabuf8sqoiw76n2ty 
        foreign key (BOOK_ID) 
        references USER (ID);

    alter table USERS_BOOKS 
        add constraint FK_bqnmno9e9vab4431hpm6kd8bi 
        foreign key (USER_ID) 
        references BOOK (id);
