create
    definer = root@localhost procedure findProductById(IN fId int)
BEGIN
    SELECT * FROM Product WHERE ProductID = fId;
end;

