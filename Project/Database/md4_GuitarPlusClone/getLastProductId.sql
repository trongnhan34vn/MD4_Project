create
    definer = root@localhost procedure getLastProductId()
BEGIN
    SELECT MAX(ProductID) FROM Product;
end;

