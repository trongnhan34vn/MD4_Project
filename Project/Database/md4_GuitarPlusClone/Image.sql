create table Image
(
    ImageID   int auto_increment
        primary key,
    URL       text null,
    ProductID int  null,
    constraint image_ibfk_1
        foreign key (ProductID) references Product (ProductID)
);

create index ProductID
    on Image (ProductID);

