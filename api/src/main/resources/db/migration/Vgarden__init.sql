create table garden_images (
    garden_id bigint,
    garden_image_id bigint not null auto_increment,
    image_url varchar(255),
    primary key (garden_image_id)
) engine=InnoDB;
create table garden_likes (
    created_date datetime(6),
    garden_id bigint,
    garden_like_id bigint not null auto_increment,
    member_id bigint,
    primary key (garden_like_id)
) engine=InnoDB;
create table gardens (
    created_date date,
    is_deleted bit,
    is_equipment bit,
    is_toilet bit,
    is_waterway bit,
    last_modified_date date,
    latitude float(53) not null,
    longitude float(53) not null,
    recruit_end_date date,
    recruit_start_date date,
    reported_score integer,
    use_end_date date,
    use_start_date date,
    garden_id bigint not null auto_increment,
    writer_id bigint,
    address varchar(255) not null,
    contact varchar(255),
    garden_description TEXT,
    garden_name varchar(255) not null,
    link_for_request varchar(255),
    price varchar(255),
    size varchar(255),
    garden_status enum ('ACTIVE','INACTIVE') not null,
    garden_type enum ('ALL','PUBLIC','PRIVATE') not null,
    point point not null srid 4326,
    primary key (garden_id)
) engine=InnoDB;
create table my_managed_gardens (
    use_end_date date,
    use_start_date date,
    garden_id bigint,
    member_id bigint,
    my_managed_garden_id bigint not null auto_increment,
    image_url varchar(255),
    primary key (my_managed_garden_id)
) engine=InnoDB;

create table garden_images
(
    garden_id       bigint,
    garden_image_id bigint not null auto_increment,
    image_url       varchar(255),
    primary key (garden_image_id)
) engine=InnoDB;
create table garden_likes
(
    created_date   datetime(6),
    garden_id      bigint,
    garden_like_id bigint not null auto_increment,
    member_id      bigint,
    primary key (garden_like_id)
) engine=InnoDB;
create table gardens
(
    created_date       date,
    is_deleted         bit,
    is_equipment       bit,
    is_toilet          bit,
    is_waterway        bit,
    last_modified_date date,
    latitude           float(53)    not null,
    longitude          float(53)    not null,
    recruit_end_date   date,
    recruit_start_date date,
    reported_score     integer,
    use_end_date       date,
    use_start_date     date,
    garden_id          bigint       not null auto_increment,
    writer_id          bigint,
    address            varchar(255) not null,
    contact            varchar(255),
    garden_description TEXT,
    garden_name        varchar(255) not null,
    link_for_request   varchar(255),
    price              varchar(255),
    size               varchar(255),
    garden_status      enum ('ACTIVE','INACTIVE') not null,
    garden_type        enum ('ALL','PUBLIC','PRIVATE') not null,
    point              point        not null srid 4326,
    primary key (garden_id)
) engine=InnoDB;
create table my_managed_gardens
(
    use_end_date         date,
    use_start_date       date,
    garden_id            bigint,
    member_id            bigint,
    my_managed_garden_id bigint not null auto_increment,
    image_url            varchar(255),
    primary key (my_managed_garden_id)
) engine=InnoDB;

CREATE
FULLTEXT INDEX ft_index ON gardens (garden_name) WITH PARSER ngram;
create
spatial index sp_index on gardens (point);
