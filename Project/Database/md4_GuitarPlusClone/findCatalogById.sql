create
    definer = root@localhost procedure findCatalogById(IN fId int)
SELECT * FROM Catalog WHERE CatalogID = fId;

