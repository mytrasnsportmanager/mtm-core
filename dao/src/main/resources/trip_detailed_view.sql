create or replace view `trip_detailed` AS select `b`.`vehicleid` AS `vehicleid`,`b`.`routeid` AS `routeid`,`b`.`driverid` AS `driverid`,`c`.`consignerid` 
AS `consignerid`,`a`.`source` AS `source`,`a`.`destination` AS `destination`,`a`.`rate` AS `rate`,`a`.`rate_type` AS `rate_type`,
(case when (`a`.`rate_type` = 'per\n\n\ntonne') then concat(`b`.`work_done`,' tonnes, ',`a`.`rate`,' per tonne') when (`a`.`rate_type` = 'per km') 
then concat(`b`.`work_done`,' KMs, ',`a`.`rate`,' \nper \nKM') when (`a`.`rate_type` = 'per month') then 
concat(`b`.`work_done`,' Months, ',`a`.`rate`,'per Month') else concat(`b`.`work_done`) end) 
AS `work_done_detail`,`b`.`starttime` AS `starttime`,`b`.`endtime` AS `endtime`,`c`.`consigner_name` 
AS `consigner_name`,`b`.`material_name` AS `material_name`,`d`.`name` AS `drivername`,`f`.`image_url` 
AS `image_url`,`b`.`tripid` AS `tripid`,`e`.`ownerid` AS `ownerid`,`e`.`registration_num` AS `registration_num`,`b`.`billingid` 
AS `billingid`,`b`.`work_done` AS `work_done` from ((((`route` `a` join `trip` `b` on((`a`.`routeid` = `b`.`routeid`))) 
join `owner_consigner` `c` on(((`a`.`consignerid` = `c`.`consignerid`) and (`a`.`ownerid` = `c`.`ownerid`)))) join 
`vehicle` `e` on((`e`.`vehicleid` = `b`.`vehicleid`))) join `vehicledriver` `d` on((`e`.`driverid` = `d`.`driverid`)) join `consigner` `f` on((`a`.`consignerid` = `f`.`consignerid`))) 
union all select `trip_detailed_hist`.`vehicleid` AS `vehicleid`,`trip_detailed_hist`.`routeid` AS `routeid`,`trip_detailed_hist`.`driverid` 
AS `driverid`,`trip_detailed_hist`.`consignerid` AS `consignerid`,`trip_detailed_hist`.`source` AS `source`,`trip_detailed_hist`.`destination` 
AS `destination`,`trip_detailed_hist`.`rate` AS `rate`,`trip_detailed_hist`.`rate_type` AS `rate_type`,`trip_detailed_hist`.`work_done_detail` 
AS `work_done_detail`,`trip_detailed_hist`.`starttime` AS `starttime`,`trip_detailed_hist`.`endtime` AS `endtime`,`trip_detailed_hist`.`consigner_name` 
AS `consigner_name`,`trip_detailed_hist`.`material_name` AS `material_name`,`trip_detailed_hist`.`drivername` 
AS `drivername`,`trip_detailed_hist`.`image_url` AS `image_url`,`trip_detailed_hist`.`tripid` AS `tripid`,`trip_detailed_hist`.`ownerid` 
AS `ownerid`,`trip_detailed_hist`.`registration_num` AS `registration_num`,`trip_detailed_hist`.`billingid` 
AS `billingid`,`trip_detailed_hist`.`work_done` AS `work_done` from `trip_detailed_hist`;