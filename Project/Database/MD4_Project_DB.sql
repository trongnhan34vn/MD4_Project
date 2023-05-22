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
    Role      INT          NOT NULL,
    Status    BIT DEFAULT (1)
);

CREATE TABLE Cart
(
    CartID    INT PRIMARY KEY AUTO_INCREMENT,
    UserID    INT,
    FOREIGN KEY (UserID) REFERENCES User (UserID),
    ProductID INT,
    FOREIGN KEY (ProductID) REFERENCES Product (ProductID),
    Quantity  INT,
    Status int not null
);

CREATE TABLE `Order`
(
    OrderId     INT PRIMARY KEY AUTO_INCREMENT,
    UserId      INT,
    FOREIGN KEY (UserId) REFERENCES User (UserID),
    Phone       VARCHAR(10),
    Address     TEXT,
    OrderDate   DATETIME DEFAULT NOW(),
    OrderStatus BIT      DEFAULT 0
);

CREATE TABLE OrderDetail
(
    ODId      INT PRIMARY KEY AUTO_INCREMENT,
    OrderId   INT,
    FOREIGN KEY (OrderId) REFERENCES `Order` (OrderId),
    ProductId INT,
    Quantity  INT
);

CREATE TABLE Feedback
(
    FeedbackID INT PRIMARY KEY AUTO_INCREMENT,
    Message    TEXT,
    OrderID    INT,
    FOREIGN KEY (OrderID) REFERENCES `Order` (OrderId)
);

# Insert

INSERT INTO User
VALUES (0, '', 'admin', '', 'admin@gmail.com', 1, '1998-01-01', 'pikachu123', 0, 1);

INSERT INTO Catalog (CatalogName)
VALUES ('Guitar'),
       ('String'),
       ('Audio Interface & Recording'),
       ('Pickup & EQ');

# Insert

# Delimiter

DELIMITER //
CREATE PROCEDURE createImage(nURL TEXT, nPId INT)
BEGIN
    INSERT INTO Image (URL, ProductID) VALUES (nURL, nPId);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createProduct(nPName VARCHAR(255), nCatID INT, nDesc TEXT, nQuantity INT, nPrice FLOAT)
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

DELIMITER //
CREATE PROCEDURE checkLogin(cEmail VARCHAR(255), cPassword VARCHAR(255))
BEGIN
    SELECT * FROM User WHERE Email like cEmail AND Password like cPassword;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getHotProducts()
BEGIN
    SELECT * FROM Product LIMIT 12;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getOutStandingProducts()
BEGIN
    SELECT * FROM Product ORDER BY RAND() LIMIT 7;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findCartByUserId(fId INT)
BEGIN
    SELECT * FROM Cart WHERE UserID = fId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findProdOnCartUser(fUId INT, fPId INT)
BEGIN
    SELECT * FROM (SELECT * FROM Cart WHERE UserID = fUId) AS UserCart WHERE ProductID = fPId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createCart(nUID INT, nPID INT, nQuantity INT)
BEGIN
    INSERT INTO Cart (UserID, ProductID, Quantity) VALUES (nUID, nPID, nQuantity);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findCartById(fId INT)
BEGIN
    SELECT * FROM Cart WHERE CartID = fId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE updateCart(uCID INT, uQty INT)
BEGIN
    UPDATE Cart
    SET Quantity = uQty
    WHERE CartID = uCID;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE removeCartById(idDel INT)
BEGIN
    DELETE FROM Cart WHERE CartID = idDel;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE updateStatusCart(idU INT, booU BIT)
BEGIN
    UPDATE Cart
    SET Status = booU
    where CartID = idU;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findSelectedCartInUserCart(uId INT)
BEGIN
    SELECT * FROM Cart WHERE UserID = uId AND Status = TRUE;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createOrder(cUserID INT, cPhone VARCHAR(10), cAddress TEXT, cTotal FLOAT, OUT lastId INT)
BEGIN
    INSERT INTO `Order` (UserId, Phone, Address, Total) VALUES (cUserID, cPhone, cAddress, cTotal);
    SET lastId = LAST_INSERT_ID();
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findOrderById(fId INT)
BEGIN
    SELECT * FROM `Order` WHERE OrderId = fId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createOrderDetail(cOID INT, cPID INT, cQuantity INT)
BEGIN
    INSERT INTO OrderDetail (OrderId, ProductId, Quantity) VALUES (cOID, cPID, cQuantity);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findOrderDetailByOrderId(fOID INT)
BEGIN
    SELECT * FROM OrderDetail WHERE OrderId = fOID;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findOrderByUserId(fUId INT)
BEGIN
    SELECT * FROM `Order` WHERE UserId = fUId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE pagingAdminProduct(pageNum INT, countPage INT)
BEGIN
    DECLARE startNum INT;
    SET startNum = (pageNum - 1) * countPage;
    SELECT * FROM Product LIMIT startNum, countPage;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE pagingAdminUSER(pageNum INT, countPage INT)
BEGIN
    DECLARE startNum INT;
    SET startNum = (pageNum - 1) * countPage;
    SELECT * FROM User WHERE UserID > 0 LIMIT startNum, countPage ;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE updateUser(updateUserId INT, uAva TEXT, uFName VARCHAR(255), uLName VARCHAR(255), uEmail VARCHAR(255), uGender BIT, uBirthDate DATE, uPassword VARCHAR(255), uRole INT, uStatus BIT)
BEGIN
    UPDATE User
        SET Avatar = uAva, FirstName = uFName, LastName = uLName, Email = uEmail, Gender = uGender, BirthDate = uBirthDate, Password = uPassword, Role = uRole, User.Status = uStatus WHERE UserID = updateUserId;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE pagingAdminOrder(pageNum INT, countPage INT)
BEGIN
    DECLARE startNum INT;
    SET startNum = (pageNum - 1) * countPage;
    SELECT * FROM `Order` WHERE UserID > 0 LIMIT startNum, countPage ;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findAllOrder()
BEGIN
    SELECT * FROM `Order`;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE updateStatOrder(uOrdStat INT, uOrdID INT)
BEGIN
    UPDATE `Order`
        SET OrderStatus = uOrdStat
    WHERE OrderId = uOrdID;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createFeed(cMessage TEXT, cOID INT)
BEGIN
    INSERT INTO Feedback (Message, OrderID) VALUES (cMessage,cOID);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE findFeedByOrderId(fOID INT)
BEGIN
    SELECT * FROM Feedback WHERE OrderID = fOID;
end //
DELIMITER ;
