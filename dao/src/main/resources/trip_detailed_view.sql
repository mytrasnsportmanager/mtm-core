CREATE or replace VIEW trip_detailed AS select b.vehicleid AS vehicleid,b.routeid AS routeid,b.driverid AS driverid,c.consignerid AS consignerid,a.source AS source,a.destination AS destination,
a.rate AS rate,a.rate_type AS rate_type,(case when (a.rate_type = 'per\n\n\ntonne') then concat(b.work_done,' tonnes, ',a.rate,' per tonne')
when (a.rate_type = 'per km') then concat(b.work_done,' KMs, ',a.rate,' \nper \nKM') when (a.rate_type = 'per month')
then concat(b.work_done,' Months, ',a.rate,'per Month') else concat(b.work_done) end) AS work_done_detail,b.starttime AS starttime,b.endtime AS endtime,
c.consigner_name AS consigner_name,b.material_name AS material_name,d.name AS drivername,c.image_url AS image_url,b.tripid AS tripid,e.ownerid AS ownerid,
e.registration_num AS registration_num,b.billingid AS billingid,b.work_done AS work_done from ((((route a join trip b on((a.routeid = b.routeid))) join
owner_consigner c on(((a.consignerid = c.consignerid) and (a.ownerid = c.ownerid)))) join vehicle e on((e.vehicleid = b.vehicleid))) join
vehicledriver d on((e.driverid = d.driverid)))
union all
select * from trip_detailed_hist