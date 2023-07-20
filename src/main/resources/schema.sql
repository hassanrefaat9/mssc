create table Post(
    id int auto_increment primary key ,
    title varchar(255) not null ,
    content text not null
);

insert into Post(title, content) VALUES ( 'Hello, World','My First Blog Post' );