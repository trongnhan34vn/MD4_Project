create
    definer = root@localhost procedure createProduct(IN nPName varchar(255), IN nCatID int, IN nDesc varchar(255),
                                                     IN nQuantity int, IN nPrice float)
BEGIN
    INSERT INTO Product (ProductName, CatalogID, Description, Quantity, Price)
        VALUES (nPName, nCatID, nDesc, nQuantity, nPrice);
end;

