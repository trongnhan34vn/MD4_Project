create
    definer = root@localhost procedure deleteProductById(IN delId int)
BEGIN
    DELETE FROM Product WHERE ProductID = delId;
end;

