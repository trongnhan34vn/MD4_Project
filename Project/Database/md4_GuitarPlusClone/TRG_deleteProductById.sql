create definer = root@localhost trigger TRG_deleteProductById
    before delete
    on Product
    for each row
BEGIN
        DELETE FROM Image WHERE Image.ProductID = OLD.ProductID;
    end;

