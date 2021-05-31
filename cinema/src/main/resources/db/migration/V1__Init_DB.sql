create table cinema (
    id bigint not null,
    address varchar(255),
    total_cinema_hall integer not null,
    work_time varchar(255),
    primary key (id)) engine=InnoDB;

create table film (
    id bigint not null AUTO_INCREMENT,
    author_name varchar(255),
    author_tel varchar(255),
    bought_places integer not null,
    cost_film integer not null,
    current_places integer not null,
    description_film varchar(2048),
    filename varchar(255),
    first_show_date date,
    free_places integer not null,
    last_show_date date,
    name_film varchar(255),
    place_film varchar(255),
    tag_film varchar(255),
    user_id bigint,
    primary key (id)) engine=InnoDB;

create table film_session (
    id bigint not null AUTO_INCREMENT,
    buyer_phone varchar(255),
    buyer_username varchar(255),
    seller_username varchar(255),
    session_date datetime(6),
    session_total_price double precision not null,
    total_tickets integer not null,
    film_id bigint,
    buyer_id bigint,
    seller_id bigint,
    primary key (id)) engine=InnoDB;

create table user_role (
    user_id bigint not null,
    roles varchar(255)) engine=InnoDB;

create table usr (
    id bigint not null AUTO_INCREMENT,
    activate_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255),
    phone varchar(255),
    username varchar(255),
    primary key (id)) engine=InnoDB;

alter table film
    add constraint FK5dyd6el58ke9g8p5kbb0rye6y
    foreign key (user_id) references usr (id);

alter table film_session
    add constraint FK7rtf3fh549wsvmumovd6v2fsv
    foreign key (film_id) references film (id);

alter table film_session
    add constraint FKtn8sf09a7yvkjdg141tr27aa8
    foreign key (buyer_id) references usr (id);

alter table film_session
    add constraint FKrabkgc25vw2iieochrxdv8p93
    foreign key (seller_id) references usr (id);

alter table user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5
    foreign key (user_id) references usr (id)