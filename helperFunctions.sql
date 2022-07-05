DELIMITER $$
CREATE DEFINER=`server_1064225`@`%` FUNCTION `getClientidByName`(clientName varchar(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN
	declare clientid int default -1;
    SELECT client_id from clients where name = clientName into clientid;
    return clientid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`server_1064225`@`%` FUNCTION `getClientidByUUID`(clientUUID varchar(255)) RETURNS int(11)
    DETERMINISTIC
BEGIN
	declare clientid int default -1;
    SELECT client_id from clients where playeruuid = clientUUID into clientid;
    return clientid;
END$$
DELIMITER ;
