create
    definer = root@localhost procedure findImagesByProductId(IN pId int)
BEGIN
    SELECT * FROM Image WHERE ProductID = pId;
end;

