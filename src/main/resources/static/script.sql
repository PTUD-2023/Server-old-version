create database health_insurance;
use health_insurance;

DROP TABLE IF EXISTS `health_insurance`.`refresh_token`;
DROP TABLE IF EXISTS`health_insurance`.`user_accounts`;
DROP TABLE IF EXISTS `health_insurance`.`confirm_code`;
drop table if exists `health_insurance`.`health_information`;
drop table if exists `health_insurance`.`relatives`;
drop table if exists `health_insurance`.`insurance_type`;
drop table if exists `health_insurance`.`insurance_plan`;
drop table if exists `health_insurance`.`insurance_payment`;
drop table if exists `health_insurance`.`registration_form`;
drop table if exists `health_insurance`.`insurance_policy`;



create table health_information
(
    id         bigint auto_increment primary key,
    general_health_condition text not null,
    medical_history text not null,
    medicines_and_treatment text not null,
    family_history text not null,
    lifestyle_and_risk_factors text not null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null
); 

create table user_accounts
(
    id         bigint auto_increment primary key,
    email      varchar(50)                                                                  not null,
    password   varchar(500)                                                                  not null,
    last_name  varchar(50)                                                                  not null default 'User',
    first_name varchar(50)                                                                  not null default 'New',
    phone      varchar(20)                                                                  null,
    birthday date not null,
    `gender` enum('male','female') null,
    address text,
    CMND char(9) not null,
    health_infor_id bigint,
    `role` enum('ROLE_USER','ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER',
    avatar     json                                                                         null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'not_activated',
    unique (email,CMND),
    foreign key (health_infor_id) references health_information(id)
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

create table relatives
(
    id         bigint auto_increment primary key,
    email      varchar(50) not null,
    name varchar(50) not null ,
    phone      varchar(20)  not null,
    birthday date not null,
    `gender` enum('male','female') not null,
    address text not null,
    CMND char(9) not null,
    health_infor_id bigint,
    user_account_id bigint not null,
    relationship_with_buyers text not null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    foreign key (health_infor_id) references health_information(id),
    foreign key (user_account_id) references user_accounts(id)
);

create table insurance_type
(
    id         bigint auto_increment primary key,
    type_name text,
    description text,
    coverage_details text,
    premium_rate float,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'activated'
);

create table insurance_plan
(
    id         bigint auto_increment primary key,
    name text,
    cost int not null,
    duration varchar(20) default '1 năm',
    coverage_amount int not null,
    insurance_type_id bigint not null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'activated',
    foreign key (insurance_type_id) references insurance_type(id)
);

create table insurance_payment
(
    id         bigint auto_increment primary key,
    amount int not null,
    payment_date date not null,
    payment_method varchar(20) not null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'unpaid',
    insurance_type_id bigint not null,
    foreign key (insurance_type_id) references insurance_type(id)
);


create table registration_form
(
    id         bigint auto_increment primary key,
    apply_date date not null,
    start_date date not null,
    end_date date not null,
    note text,
    user_account_id bigint,
    applicant_type varchar(30) default 'both',
    relative_id bigint,
    insurance_plan_id bigint,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'not_activated',
    foreign key (user_account_id) references user_accounts(id),
    foreign key (relative_id) references relatives(id),
    foreign key (insurance_plan_id) references insurance_plan(id)
);

create table insurance_policy
(
    id         bigint auto_increment primary key,
    apply_date date not null,
    start_date date not null,
    end_date date not null,
    registration_form_id bigint not null,
    insurance_payment_id bigint not null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'not_activated',
    foreign key (registration_form_id) references registration_form(id),
    foreign key (insurance_payment_id) references insurance_payment(id)
);

create table claim_requests
(
    id         bigint auto_increment primary key,
    amount int not null,
    request_date date not null,
    request_content text not null,
    insurance_policy_id bigint not null,
    user_account_id bigint not null,
    created_at timestamp              default current_timestamp                             null,
    updated_at timestamp              default current_timestamp on update current_timestamp null,
    status varchar(20) default 'unpaid',
    foreign key (insurance_policy_id) references insurance_policy(id),
    foreign key (user_account_id) references user_accounts(id)
);




