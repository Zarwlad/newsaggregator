create table if not exists data_source
(
    id uuid not null
        constraint data_source_pk
            primary key,
    created_at timestamp with time zone not null,
    parsed_doc_api_type varchar(50) not null,
    page_count integer not null,
    source bytea not null,
    original_path varchar,
    filename varchar
);

alter table data_source owner to postgres;

create table if not exists parse_iteration
(
    id uuid not null
        constraint parse_iteration_pk
            primary key,
    stop_phrase varchar,
    data_source_id uuid not null
        constraint parse_iteration_data_source_id_fk
            references data_source
);

alter table parse_iteration owner to postgres;

create table if not exists change_log
(
    id uuid not null
        constraint change_log_pk
            primary key,
    created_at timestamp with time zone not null,
    source_text text not null,
    beatified_text text not null,
    change_log_date date,
    data_source_id uuid not null
        constraint change_log_data_source_id_fk
            references data_source,
    parse_iteration_id uuid
        constraint change_log_parse_iteration_id_fk
            references parse_iteration,
    version varchar
);

alter table change_log owner to postgres;

