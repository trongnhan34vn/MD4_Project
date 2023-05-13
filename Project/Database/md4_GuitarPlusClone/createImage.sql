create
    definer = root@localhost procedure createImage(IN nURL text, IN nPId int)
BEGIN
    INSERT INTO Image (URL, ProductID) VALUES (nURL, nPId);
end;

