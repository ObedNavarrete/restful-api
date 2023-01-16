create table rol(
    id serial not null primary key,
    nombre varchar(60),
    pasivo boolean
);

create table usuario(
    id serial not null primary key,
    nombre varchar(150),
    email varchar(80) constraint uk6dotkott2kjsp8vw4d0m25fb7 unique,
    password varchar(60) not null ,
    telefono varchar(10) constraint ukdu5v5sr43g5bfnji4vb8hg5s3 unique,
    activo boolean,
    pasivo boolean not null,
    creado_por int references usuario(id) null,
    creado_el timestamp not null,
    creado_en_ip varchar(50) not null,
    modificado_por int references usuario(id),
    modificado_el timestamp,
    modificado_en_ip varchar(50)
);

create table usuario_rol(
    usuario_id integer not null
        constraint fkiu0xsee0dmwa28nffgyf4bcvc
            references usuario
        constraint users_role_user_id_fkey
            references usuario,
    rol_id  integer not null
        constraint fk3qjq7qsiigxa82jgk0i0wuq3g
            references rol
        references rol,
    constraint userid_roleid_unique
        unique (usuario_id, rol_id)
);