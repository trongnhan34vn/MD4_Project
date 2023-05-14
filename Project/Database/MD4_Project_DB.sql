CREATE DATABASE md4_GuitarPlusClone;

USE md4_GuitarPlusClone;

CREATE TABLE Catalog
(
    CatalogID     INT PRIMARY KEY AUTO_INCREMENT,
    CatalogName   VARCHAR(255) NOT NULL,
    CatalogStatus BIT DEFAULT (1)
);

CREATE TABLE Product
(
    ProductID     INT PRIMARY KEY AUTO_INCREMENT,
    ProductName   VARCHAR(255) NOT NULL,
    CatalogID     INT          NOT NULL,
    FOREIGN KEY (CatalogID) REFERENCES Catalog (CatalogID),
    Description   TEXT,
    Quantity      INT,
    Price         FLOAT,
    ProductStatus BIT DEFAULT (1)
);

CREATE TABLE Image
(
    ImageID   INT PRIMARY KEY AUTO_INCREMENT,
    URL       TEXT,
    ProductID INT,
    FOREIGN KEY (ProductID) REFERENCES Product (ProductID)
);

CREATE TABLE User
(
    UserID    INT PRIMARY KEY AUTO_INCREMENT,
    Avatar    TEXT,
    FirstName VARCHAR(255),
    LastName  VARCHAR(255),
    Email     VARCHAR(255) NOT NULL,
    Gender    BIT DEFAULT (1),
    BirthDate DATE,
    Password  VARCHAR(255) NOT NULL,
    Role      INT          NOT NULL
);

INSERT INTO User
VALUES (0, '', 'admin', '', 'admin@gmail.com', 1, '1998-01-01', 'pikachu123', 0);

INSERT INTO Catalog (CatalogName)
VALUES ('Guitar'),
       ('String'),
       ('Audio Interface & Recording'),
       ('Pickup & EQ');

DELIMITER //
CREATE PROCEDURE createImage(nURL TEXT, nPId INT)
BEGIN
    INSERT INTO Image (URL, ProductID) VALUES (nURL, nPId);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createProduct(nPName VARCHAR(255), nCatID INT, nDesc VARCHAR(255), nQuantity INT, nPrice FLOAT)
BEGIN
    INSERT INTO Product (ProductName, CatalogID, Description, Quantity, Price)
    VALUES (nPName, nCatID, nDesc, nQuantity, nPrice);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findProductById(fId INT)
BEGIN
    SELECT * FROM Product WHERE ProductID = fId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findImagesByProductId(pId INT)
BEGIN
    SELECT * FROM Image WHERE ProductID = pId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findImageById(fid INT)
BEGIN
    SELECT * FROM Image WHERE ImageID = fid;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findCatalogById(fId INT)
SELECT *
FROM Catalog
WHERE CatalogID = fId;
DELIMITER ;

DELIMITER //
CREATE TRIGGER TRG_deleteProductById
    BEFORE DELETE
    ON Product
    FOR EACH ROW
BEGIN
    DELETE FROM Image WHERE Image.ProductID = OLD.ProductID;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE deleteProductById(delId INT)
BEGIN
    DELETE FROM Product WHERE ProductID = delId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getLastProductId()
BEGIN
    SELECT MAX(ProductID) FROM Product;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE deleteImageById(delId INT)
BEGIN
    DELETE FROM Image WHERE ImageID = delId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE updateProduct(
    uId INT,
    uName VARCHAR(255),
    uCatId INT,
    uDescription TEXT,
    uQuantity INT,
    uPrice FLOAT,
    uProdStat BIT
)
BEGIN
    UPDATE Product
    SET ProductName   = uName,
        CatalogID     = uCatId,
        Description   = uDescription,
        Quantity      = uQuantity,
        Price         = uPrice,
        ProductStatus = uProdStat
    WHERE ProductID = uId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findUserByEmail(fEmail VARCHAR(255))
BEGIN
    SELECT * FROM User WHERE Email = fEmail;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createUser(nAvatar TEXT, nFirstName VARCHAR(255), nLastName VARCHAR(255), nEmail VARCHAR(255),
                            nGender BIT, nBirthDay DATE, nPassword VARCHAR(255), nRole INT)
BEGIN
    INSERT INTO User (Avatar, FirstName, LastName, Gender, Email, BirthDate, Password, Role)
    VALUES (nAvatar, nFirstName, nLastName, nGender, nEmail, nBirthDay, nPassword, nRole);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findUserById(fId INT)
BEGIN
    SELECT * FROM User WHERE UserID = fId;
end //
DELIMITER ;


