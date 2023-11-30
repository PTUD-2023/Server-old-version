DROP TABLE IF EXISTS `health_insurance`.`refresh_token`;
DROP TABLE `health_insurance`.`user_accounts`;
DROP TABLE `health_insurance`.`confirm_code`;

create table user_accounts
(
    id         bigint auto_increment primary key,
    email      varchar(50)                                                                  not null,
    password   varchar(500)                                                                  not null,
    last_name  varchar(50)                                                                  not null default 'User',
    first_name varchar(50)                                                                  not null default 'New',
    phone      varchar(20)                                                                  null,
    birthday date,
    `gender` enum('male','female','unknown') null,
    `role` enum('ROLE_USER','ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER',
    avatar     json                                                                         null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'not_activated',
    unique (email)
);

create table refresh_token
(
    id bigint auto_increment primary key,
    user_account_id bigint,
    token varchar(255) not null unique,
    expiry_date timestamp not null,
    foreign key (user_account_id) references user_accounts(id)
);

create table confirm_code
(
    id bigint auto_increment primary key,
    user_account_id bigint,
    code varchar(6) not null unique,
    expiry_date timestamp not null,
    foreign key (user_account_id) references user_accounts(id)
);