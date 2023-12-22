
create table garden_images (
        garden_id bigint,
        garden_image_id bigint not null auto_increment,
        image_url varchar(255),
        primary key (garden_image_id)
) ENGINE=InnoDB;

create table garden_likes (
        created_date datetime(6),
        garden_id bigint,
        garden_like_id bigint not null auto_increment,
        member_id bigint,
        primary key (garden_like_id)
 ) ENGINE=InnoDB;

create table gardens (
        deleted_max_score integer not null,
        is_deleted bit,
        is_equipment bit,
        is_toilet bit,
        is_waterway bit,
        latitude float(53) not null,
        longitude float(53) not null,
        min_description_length integer not null,
        reported_score integer,
        created_date datetime(6),
        garden_id bigint not null auto_increment,
        last_modified_date datetime(6),
        recruit_end_date datetime(6),
        recruit_start_date datetime(6),
        use_end_date datetime(6),
        use_start_date datetime(6),
        writer_id bigint,
        address varchar(255) not null,
        contact varchar(255),
        garden_description TEXT,
        garden_name varchar(255) not null,
        link_for_request varchar(255),
        price varchar(255),
        size varchar(255),
        garden_status enum ('ACTIVE','INACTIVE') not null,
        garden_type enum ('All','PUBLIC','PRIVATE') not null,
        point geometry not null,
        primary key (garden_id)
    )  ENGINE=InnoDB;
create table tended_gardens (
        latitude float(53),
        longitude float(53),
        use_end_date date,
        use_start_date date,
        garden_id bigint,
        id bigint not null auto_increment,
        member_id bigint,
        address varchar(255),
        garden_name varchar(255),
        image_url varchar(255),
        primary key (id)
) ENGINE=InnoDB;

create table viewed_gardens (
        created_date datetime(6),
        garden_id bigint,
        garden_view_id bigint not null auto_increment,
        member_id bigint,
        primary key (garden_view_id)
) ENGINE=InnoDB;

--report 관련 컬럼 수정--
ALTER TABLE reports
MODIFY COLUMN garden_report_type ENUM('FAKED_SALE','SPAMMING','SWEAR_WORD','SENSATIONAL','PERSONAL_INFORMATION_EXPOSURE','COMMENTS');

--index 추가--
CREATE FULLTEXT INDEX ft_index ON gardens (garden_name) WITH PARSER ngram;
create spatial index sp_index on gardens (point);
