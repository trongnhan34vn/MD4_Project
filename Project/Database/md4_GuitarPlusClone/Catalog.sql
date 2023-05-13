create table Catalog
(
    CatalogID     int auto_increment
        primary key,
    CatalogName   varchar(255)    not null,
    CatalogStatus bit default (1) null
);

