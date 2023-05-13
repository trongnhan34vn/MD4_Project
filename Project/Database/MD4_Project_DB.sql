CREATE DATABASE md4_GuitarPlusClone;

USE md4_GuitarPlusClone;

CREATE TABLE Catalog
(
  CatalogID INT PRIMARY KEY AUTO_INCREMENT,
  CatalogName VARCHAR(255) NOT NULL ,
  CatalogStatus BIT DEFAULT (1)
);

CREATE TABLE Product
(
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(255) NOT NULL ,
    CatalogID INT NOT NULL ,
    FOREIGN KEY (CatalogID) REFERENCES Catalog(CatalogID),
    Description TEXT,
    Quantity INT,
    Price FLOAT,
    ProductStatus BIT DEFAULT (1)
);

CREATE TABLE Image
(
    ImageID INT PRIMARY KEY AUTO_INCREMENT,
    URL TEXT,
    ProductID INT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

INSERT INTO Catalog (CatalogName) VALUES
('Guitar'),
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
    SELECT * FROM Catalog WHERE CatalogID = fId;
DELIMITER ;

DELIMITER //
CREATE TRIGGER  TRG_deleteProductById
    BEFORE DELETE ON Product
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
