create table Product
(
    ProductID     int auto_increment
        primary key,
    ProductName   varchar(255)    not null,
    CatalogID     int             not null,
    Description   text            null,
    Quantity      int             null,
    Price         float           null,
    ProductStatus bit default (1) null,
    constraint product_ibfk_1
        foreign key (CatalogID) references Catalog (CatalogID)
);

create index CatalogID
    on Product (CatalogID);

