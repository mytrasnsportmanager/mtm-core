create or replace VIEW earned_received AS select v.registration_num AS registration_num,(t.work_done * r.rate) AS amount,t.starttime AS eventtime,'earned'
AS type,t.challanid AS referenceid,0 AS work,concat('E',t.tripid) AS erid,t.vehicleid AS vehicleid,r.consignerid AS consignerid, t.billingid as billingid from
((trip t join route r on((t.routeid = r.routeid))) join vehicle v on((t.vehicleid = v.vehicleid))) union all select v2.registration_num AS
registration_num,tx.amount AS amount,tx.txn_date AS txn_Date,'received' AS type,tx.txnid AS txnid,tx.txn_type AS txn_type,concat('T',tx.txnid)
 AS erid,tx.vehicleid AS vehicleid,tx.consignerid AS consignerid, tx.billingid as billingid from (txn tx join vehicle v2 on((tx.vehicleid = v2.vehicleid)))
union all
select * from earned_received_hist