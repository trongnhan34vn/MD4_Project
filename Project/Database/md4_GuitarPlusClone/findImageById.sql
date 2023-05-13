create
    definer = root@localhost procedure findImageById(IN fid int)
BEGIN
    SELECT * FROM Image WHERE ImageID = fid;
end;

